package ga.rugal.jpt.core.service;

import ga.rugal.jpt.common.tracker.common.Torrent;
import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.common.tracker.server.TrackedPeer;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.common.tracker.server.TrackerResponseException;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Rugal Bernstein
 */
public interface Tracker
{

    /**
     * Announce a new torrent on this tracker.
     *
     * <p>
     * The fact that torrents must be announced here first makes this tracker a
     * closed BitTorrent tracker: it will only accept clients for torrents it
     * knows about, and this list of torrents is managed by the program
     * instrumenting this TrackerImpl class.
     * </p>
     *
     * @param torrent The Torrent object to start tracking.
     *
     * @return The torrent object for this torrent on this tracker. This may be
     *         different from the supplied Torrent object if the tracker already
     *         contained a torrent with the same hash.
     */
    TrackedTorrent announce(TrackedTorrent torrent);

    boolean containsKey(String hash);

    TrackedTorrent get(String hash);

    boolean isRunning();

    /**
     * Stop announcing the given torrent.
     *
     * @param torrent The Torrent object to stop tracking.
     */
    void remove(Torrent torrent);

    /**
     * Start the tracker thread.
     */
    void start();

    void stop();

    TrackedPeer update(TrackerUpdateBean bean) throws TrackerResponseException, UnsupportedEncodingException;

}
