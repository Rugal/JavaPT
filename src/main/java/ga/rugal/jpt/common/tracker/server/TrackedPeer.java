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

import config.SystemDefaultProperties;
import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.common.tracker.bcodec.BEValue;
import ga.rugal.jpt.common.tracker.common.Peer;
import ga.rugal.jpt.common.tracker.common.Torrent;
import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * A BitTorrent tracker peer.
 *
 * <p>
 * Represents a peer exchanging on a given torrent. In this implementation, we don't really care about the status of the
 * peers and how much they have downloaded / exchanged because we are not a torrent exchange and don't need to keep
 * track of what peers are doing while they're downloading. We only care about when they start, and when they are done.
 * </p>
 *
 * <p>
 * We also never expire peers automatically. Unless peers send a STOPPED announce request, they remain as long as the
 * torrent object they are a part of.
 * </p>
 */
@Slf4j
public class TrackedPeer extends Peer
{

    private long uploaded;

    private long downloaded;

    private long left;

    private final Torrent torrent;

    /**
     * Represents the state of a peer exchanging on this torrent.
     *
     * <p>
     * Peers can be in the STARTED state, meaning they have announced themselves to us and are eventually exchanging
     * data with other peers. Note that a peer starting with a completed file will also be in the started state and will
     * never notify as being in the completed state. This information can be inferred from the fact that the peer
     * reports 0 bytes left to download.
     * </p>
     *
     * <p>
     * Peers enter the COMPLETED state when they announce they have entirely downloaded the file. As stated above, we
     * may also elect them for this state if they report 0 bytes left to download.
     * </p>
     *
     * <p>
     * Peers enter the STOPPED state very briefly before being removed. We still pass them to the STOPPED state in case
     * someone else kept a reference on them.
     * </p>
     */
    public enum PeerState
    {

        UNKNOWN,
        STARTED,
        COMPLETED,
        STOPPED;
    };

    private PeerState state;

    private Date lastAnnounce;

    /**
     * Instantiate a new tracked peer for the given torrent.
     *
     * @param torrent    The torrent this peer exchanges on.
     * @param ip         The peer's IP address.
     * @param port       The peer's port.
     * @param peerIdByte The byte-encoded peer ID.
     */
    public TrackedPeer(Torrent torrent, String ip, int port, ByteBuffer peerIdByte)
    {
        this(torrent, ip, port, peerIdByte, 0l, 0l, 0l);
    }

    public TrackedPeer(Torrent torrent, String ip, int port, ByteBuffer peerIdByte, long uploaded, long downloaded, long left)
    {
        super(ip, port, peerIdByte);
        this.torrent = torrent;
        // Instantiated peers start in the UNKNOWN state.
        this.state = PeerState.UNKNOWN;
        this.lastAnnounce = null;
        this.uploaded = uploaded;
        this.downloaded = downloaded;
        this.left = left;
    }

    /**
     * Update this peer's state and information.
     *
     * <p>
     * <b>Note:</b> if the peer reports 0 bytes left to download, its state will be automatically be set to COMPLETED.
     * </p>
     *
     * @param bean
     */
    public void update(TrackerUpdateBean bean)
    {
        if (PeerState.STARTED == bean.getState() && bean.getLeft() == 0)
        {
            bean.setState(PeerState.COMPLETED);
        }

        if (bean.getState() != this.state)
        {
            //Log state change
            LOG.info(CommonLogContent.STATUS_UPDATE, this, bean.getState().name().toLowerCase(), this.torrent);
        }
        this.state = bean.getState();
        this.lastAnnounce = new Date();
        this.uploaded = bean.getUploaded();
        this.downloaded = bean.getDownloaded();
        this.left = bean.getLeft();
    }

    /**
     * Tells whether this peer has completed its download and can thus be considered a seeder.
     *
     * @return
     */
    public boolean isCompleted()
    {
        return PeerState.COMPLETED.equals(this.state);
    }

    /**
     * Returns how many bytes the peer reported it has uploaded so far.
     *
     * @return
     */
    public long getUploaded()
    {
        return this.uploaded;
    }

    /**
     * Returns how many bytes the peer reported it has downloaded so far.
     *
     * @return
     */
    public long getDownloaded()
    {
        return this.downloaded;
    }

    /**
     * Returns how many bytes the peer reported it needs to retrieve before its download is complete.
     *
     * @return
     */
    public long getLeft()
    {
        return this.left;
    }

    /**
     * Tells whether this peer has checked in with the tracker recently.
     *
     * <p>
     * Non-fresh peers are automatically terminated and collected by the Tracker.
     * </p>
     *
     * @return
     */
    public boolean isFresh()
    {
        return (this.lastAnnounce != null
                && (this.lastAnnounce.getTime() + (SystemDefaultProperties.FRESH_TIME_SECONDS * 1000)
                    > new Date().getTime()));
    }

    /**
     * Returns a BEValue representing this peer for inclusion in an announce reply from the tracker.
     *
     * The returned BEValue is a dictionary containing the peer ID (in its original byte-encoded form), the peer's IP
     * and the peer's port.
     *
     * @return
     *
     * @throws java.io.UnsupportedEncodingException
     */
    public BEValue toBEValue() throws UnsupportedEncodingException
    {
        Map<String, BEValue> peer = new HashMap<>();
        if (this.hasPeerId())
        {
            peer.put("peer id", new BEValue(this.getPeerIdByte().array()));
        }
        peer.put("ip", new BEValue(this.getIp(), SystemDefaultProperties.BYTE_ENCODING));
        peer.put("port", new BEValue(this.getPort()));
        return new BEValue(peer);
    }
}
