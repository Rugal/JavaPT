/**
 * Copyright (C) 2011-2012 Turn, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ga.rugal.jpt.common.tracker.server;

import com.mongodb.gridfs.GridFSDBFile;
import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.common.SystemDefaultProperties;
import ga.rugal.jpt.common.tracker.common.Peer;
import ga.rugal.jpt.common.tracker.common.Torrent;
import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tracked torrents are torrent for which we don't expect to have data files
 * for.
 *
 * <p>
 * {@link TrackedTorrent} objects are used by the BitTorrent tracker to
 * represent a torrent that is announced by the tracker. As such, it is not
 * expected to point to any valid local data like. It also contains some
 * additional information used by the tracker to keep track of which peers
 * exchange on it, etc.
 * </p>
 *
 * @author mpetazzoni
 */
public class TrackedTorrent extends Torrent
{

    private static final Logger logger = LoggerFactory.getLogger(TrackedTorrent.class);

    private int answerPeers;

    private int announceInterval;

    /**
     * Peers currently exchanging on this torrent.
     */
    private ConcurrentMap<String, TrackedPeer> peers;

    /**
     * Create a new tracked torrent from meta-info binary data.
     *
     * @param torrent The meta-info byte data.
     *
     * @throws IOException When the info dictionary can't be
     *                     encoded and hashed back to create the torrent's SHA-1 hash.
     */
    public TrackedTorrent(byte[] torrent) throws IOException
    {
        super(torrent, false);
        this.peers = new ConcurrentHashMap<>();
        this.answerPeers = SystemDefaultProperties.DEFAULT_ANSWER_NUM_PEERS;
        this.announceInterval = SystemDefaultProperties.DEFAULT_ANNOUNCE_INTERVAL_SECONDS;
    }

    public TrackedTorrent(Torrent torrent) throws IOException
    {
        this(torrent.getEncoded());
    }

    /**
     * Returns the map of all peers currently exchanging on this torrent.
     *
     * @return
     */
    public Map<String, TrackedPeer> getPeers()
    {
        return this.peers;
    }

    /**
     * Add a peer exchanging on this torrent.
     *
     * @param peer The new Peer involved with this torrent.
     */
    public void addPeer(TrackedPeer peer)
    {
        this.peers.put(peer.getPeerId(), peer);
    }

    /**
     * Retrieve a peer exchanging on this torrent.
     *
     * @param peerId The hexadecimal representation of the peer's ID.
     *
     * @return
     */
    public TrackedPeer getPeer(String peerId)
    {
        return this.peers.get(peerId);
    }

    public boolean containsKey(String peerId)
    {
        return peers.containsKey(peerId);
    }

    /**
     * Remove a peer from this torrent's swarm.
     *
     * @param peerId The hexadecimal representation of the peer's ID.
     *
     * @return
     */
    public TrackedPeer removePeer(String peerId)
    {
        return this.peers.remove(peerId);
    }

    /**
     * Count the number of seeders (peers in the COMPLETED state) on this
     * torrent.
     *
     * @return
     */
    public int seeders()
    {
        int count = 0;
        for (TrackedPeer peer : this.peers.values())
        {
            if (peer.isCompleted())
            {
                count++;
            }
        }
        return count;
    }

    /**
     * Count the number of leechers (non-COMPLETED peers) on this torrent.
     *
     * @return
     */
    public int leechers()
    {
        int count = 0;
        for (TrackedPeer peer : this.peers.values())
        {
            if (!peer.isCompleted())
            {
                count++;
            }
        }
        return count;
    }

    /**
     * Remove unfresh peers from this torrent.
     *
     * <p>
     * Collect and remove all non-fresh peers from this torrent. This is
     * usually called by the periodic peer collector of the BitTorrent tracker.
     * </p>
     */
    public void collectUnfreshPeers()
    {
        for (TrackedPeer peer : this.peers.values())
        {
            if (!peer.isFresh())
            {
                this.peers.remove(peer.getHexPeerId());
            }
        }
    }

    /**
     * Get the announce interval for this torrent.
     *
     * @return
     */
    public int getAnnounceInterval()
    {
        return this.announceInterval;
    }

    /**
     * Set the announce interval for this torrent.
     *
     * @param interval New announce interval, in seconds.
     */
    public void setAnnounceInterval(int interval)
    {
        if (interval <= 0)
        {
            throw new IllegalArgumentException("Invalid announce interval");
        }

        this.announceInterval = interval;
    }

    /**
     * Update this torrent's swarm from an announce event.
     * <p>
     * Either create new peer in tracked list or get it if already existed.
     * <p>
     * Then update the status of the tracked peer for future usage.
     * <p>
     * This will automatically create a new peer on a 'started' announce event,
     * and remove the peer on a 'stopped' announce event.
     * </p>
     *
     * @param bean
     *
     * @return The peer that sent us the announce request.
     *
     */
    public TrackedPeer update(TrackerUpdateBean bean)
    {
        TrackedPeer peer;
        bean.setState(TrackedPeer.PeerState.UNKNOWN);
        switch (bean.getEvent())
        {
            case STARTED:
                peer = new TrackedPeer(this, bean.getIp(), bean.getPort(), bean.getBufferPeerID());
                bean.setState(TrackedPeer.PeerState.STARTED);
                this.addPeer(peer);
                break;
            case STOPPED:
                peer = this.removePeer(bean.getPeerID());
                bean.setState(TrackedPeer.PeerState.STOPPED);
                break;
            case COMPLETED:
                peer = this.getPeer(bean.getPeerID());
                bean.setState(TrackedPeer.PeerState.COMPLETED);
                break;
            case NONE:
                peer = this.getPeer(bean.getPeerID());
                bean.setState(TrackedPeer.PeerState.STARTED);
                break;
            default:
                throw new IllegalArgumentException(CommonMessageContent.BAD_EVENT);
        }
        logger.debug(MessageFormat.format(CommonLogContent.UPDATE_CONTENT,
                                          bean.getUser().getUid(), bean.getInfoHash(), bean.getDownloaded(),
                                          bean.getUploaded(), bean.getLeft(), bean.getIp()));
        peer.update(bean);
        return peer;
    }

    /**
     * Get a list of peers we can return in an announce response for this
     * torrent.
     *
     * @param peer The peer making the request, so we can exclude it from the
     *             list of returned peers.
     *
     * @return A list of peers we can include in an announce response.
     */
    public List<Peer> getSomePeers(TrackedPeer peer)
    {
        List<Peer> peers = new LinkedList<>();

        List<TrackedPeer> candidates;
        candidates = new LinkedList<>(this.peers.values());
        Collections.shuffle(candidates);

        int count = 0;
        for (TrackedPeer candidate : candidates)
        {
            // Collect unfresh peers, and obviously don't serve them as well.
            if (!candidate.isFresh()
                || (candidate.looksLike(peer)
                && !candidate.equals(peer)))
            {
                logger.debug(CommonLogContent.STALE_PEERS, candidate);
                this.peers.remove(candidate.getHexPeerId());
                continue;
            }
            // Don't include the requesting peer in the answer.
            if (peer.looksLike(candidate))
            {
                continue;
            }
            // Collect unfresh peers, and obviously don't serve them as well.
            if (!candidate.isFresh())
            {
                logger.debug(CommonLogContent.STALE_PEERS, candidate.getHexPeerId());
                this.peers.remove(candidate.getHexPeerId());
                continue;
            }
            // Only serve at most ANSWER_NUM_PEERS peers
            if (count++ > this.answerPeers)
            {
                break;
            }
            peers.add(candidate);
        }
        return peers;
    }

    /**
     * Load a tracked torrent from the given torrent file.
     *
     * @param file The abstract {@link File} object representing the
     * <tt>.torrent</tt> file to load.
     *
     * @return
     *
     * @throws IOException When the torrent file cannot be read.
     */
    public static TrackedTorrent load(File file) throws IOException
    {
        byte[] data = FileUtils.readFileToByteArray(file);
        TrackedTorrent torrent = new TrackedTorrent(data);
        return torrent;
    }

    /**
     * Load a tracked torrent from the given byte array.
     *
     * @param dbFile
     *
     * @return
     *
     * @throws IOException When the torrent file cannot be read.
     */
    public static TrackedTorrent load(GridFSDBFile dbFile) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        dbFile.writeTo(baos);
        byte[] data = baos.toByteArray();
        TrackedTorrent torrent = new TrackedTorrent(data);
        return torrent;
    }

    /**
     * Load a tracked torrent from the given byte array.
     *
     * @param data
     *
     * @return
     *
     * @throws IOException When the torrent file cannot be read.
     */
    public static TrackedTorrent load(byte[] data) throws IOException
    {
        TrackedTorrent torrent = new TrackedTorrent(data);
        return torrent;
    }
}
