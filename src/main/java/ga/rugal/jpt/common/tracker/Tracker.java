package ga.rugal.jpt.common.tracker;

import com.turn.ttorrent.common.Torrent;
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

    public synchronized TrackedTorrent update(TrackerUpdateBean bean)
    {
        return null;
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
        if (this.cleaner == null || !this.cleaner.isAlive())
        {
            this.cleaner = new PeerCollectorThread();
            this.cleaner.start();
        }
    }

    public void stop()
    {
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
