package ga.rugal.jpt.common.tracker.server;

import ga.rugal.jpt.common.tracker.TrackerResponseException;
import ga.rugal.jpt.common.tracker.common.Torrent;
import ga.rugal.jpt.common.tracker.common.protocol.RequestEvent;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Rugal Bernstein
 */
public class Tracker
{

    private static final Logger LOG = LoggerFactory.getLogger(Tracker.class);

    private final ConcurrentMap<String, TrackedTorrent> torrents = new ConcurrentHashMap<>();

    private Thread cleaner;

    private boolean running = false;

    public synchronized TrackedPeer update(TrackerUpdateBean bean) throws TrackerResponseException, UnsupportedEncodingException
    {
        if (false == running)
        {
            throw new TrackerResponseException("Tracker is not running");
        }
        if (!this.torrents.containsKey(bean.getInfoHash()))
        {
            //report there is no such torrent
            throw new TrackerResponseException("The Requested torrent not found");
        }
        TrackedTorrent torrent = this.torrents.get(bean.getInfoHash());
        String peerId = bean.getPeerId();
        // If an event other than 'started' is specified and we also haven't
        // seen the peer on this torrent before, something went wrong. A
        // previous 'started' announce request should have been made by the
        // client that would have had us register that peer on the torrent this
        // request refers to.
        if (bean.getEvent() != null && torrent.containsKey(peerId)
            && RequestEvent.STARTED != bean.getEvent())
        {
            //send error
            throw new TrackerResponseException("Bad client event");
        }
        // When no event is specified, it's a periodic update while the client
        // is operating. If we don't have a peer for this announce, it means
        // the tracker restarted while the client was running. Consider this
        // announce request as a 'started' event.
        if ((bean.getEvent() == null || RequestEvent.NONE == bean.getEvent())
            && torrent.containsKey(peerId))
        {
            bean.setEvent(RequestEvent.STARTED);
        }
        // Update the torrent according to the announce event
        TrackedPeer peer;
        try
        {
            peer = torrent.update(bean);
        }
        catch (IllegalArgumentException iae)
        {
            throw iae;
        }
        return peer;
    }

    /**
     * Announce a new torrent on this tracker.
     *
     * <p>
     * The fact that torrents must be announced here first makes this tracker a
     * closed BitTorrent tracker: it will only accept clients for torrents it
     * knows about, and this list of torrents is managed by the program
     * instrumenting this Tracker class.
     * </p>
     *
     * @param torrent The Torrent object to start tracking.
     *
     * @return The torrent object for this torrent on this tracker. This may be
     *         different from the supplied Torrent object if the tracker already
     *         contained a torrent with the same hash.
     */
    public synchronized TrackedTorrent announce(TrackedTorrent torrent)
    {
        TrackedTorrent existing = torrents.get(torrent.getHexInfoHash());
        if (existing != null)
        {
            LOG.warn("Torrent [{}] already announced with hash {}.", existing.getName(), existing.getHexInfoHash());
            return existing;
        }
        torrents.put(torrent.getHexInfoHash(), torrent);
        LOG.info("New torrent [{}] registered with hash {}.", torrent.getName(), torrent.getHexInfoHash());
        return torrent;
    }

    /**
     * Stop announcing the given torrent.
     *
     * @param torrent The Torrent object to stop tracking.
     */
    public synchronized void remove(Torrent torrent)
    {
        if (null != torrent && !torrents.containsKey(torrent.getHexInfoHash()))
        {
            LOG.info("Torrent [{}] with hash {} is deleted.", torrent.getName(), torrent.getHexInfoHash());
            torrents.remove(torrent.getHexInfoHash());
        }
    }

    public TrackedTorrent get(String hash)
    {
        return torrents.get(hash);
    }

    public boolean containsKey(String hash)
    {
        return torrents.containsKey(hash);
    }

    /**
     * Start the tracker thread.
     */
    public void start()
    {
        running = true;
        if (this.cleaner == null || !this.cleaner.isAlive())
        {
            this.cleaner = new PeerCollectorThread();
            this.cleaner.start();
        }
    }

    public void stop()
    {
        running = false;
        if (cleaner != null && cleaner.isAlive())
        {
            cleaner.interrupt();
        }
    }

    /**
     * The unfresh peer collector thread.
     *
     * <p>
     * Every PEER_COLLECTION_FREQUENCY_SECONDS, this thread will collect
     * unfresh peers from all announced torrents.
     * </p>
     */
    private class PeerCollectorThread extends Thread
    {

        private static final int PEER_COLLECTION_FREQUENCY_SECONDS = 15;

        @Override
        public void run()
        {
            for (TrackedTorrent torrent : torrents.values())
            {
                torrent.collectUnfreshPeers();
            }
            try
            {
                Thread.sleep(PeerCollectorThread.PEER_COLLECTION_FREQUENCY_SECONDS * 1000);
            }
            catch (InterruptedException ie)
            {
                // Ignore
            }
        }
    }
}
