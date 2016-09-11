package ga.rugal.jpt.core.service;

import ga.rugal.jpt.common.tracker.common.Torrent;
import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.common.tracker.server.TrackedPeer;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.common.tracker.server.TrackerResponseException;

/**
 *
 * @author Rugal Bernstein
 */
public interface Tracker
{

    /**
     * Announce/Register a new torrent in this tracker. The list of torrents is maintained by this class.
     *
     *
     * @param torrent The Torrent object to start tracking.
     *
     * @return Get the existed one if there is a Torront object that has exact same info_hash; otherwise, return the
     *         input Torront object.
     */
    TrackedTorrent torrontRegister(TrackedTorrent torrent);

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

    /**
     * Update user profile in database by using {@code TrackerUpdateBean} information sent by user client software.
     *
     * @param bean
     *
     * @return The internal representation of user.
     *
     * @throws ga.rugal.jpt.common.tracker.server.TrackerResponseException
     */
    TrackedPeer update(TrackerUpdateBean bean) throws TrackerResponseException;

}
