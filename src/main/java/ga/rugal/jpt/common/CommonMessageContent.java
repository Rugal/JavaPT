package ga.rugal.jpt.common;

/**
 *
 * This interface contain most of referred message content used in
 * {@code Message} feedback.
 *
 * @author Rugal Bernstein
 */
public interface CommonMessageContent
{

    //User operation
    final String SAVE_USER = "User registered";

    final String UPDATE_USER = "User profile updated";

    final String DELETE_USER = "User deleted";

    final String GET_USER = "Get user profile";

    //Authentication and Authorization
    final String ACCESS_FORBIDDEN = "Unmatched combination of username and credential";

    final String PERMISSION_DENIED = "Insufficient permission";

    //Tracker operations
    final String TRACKER_STARTED = "Tracker started";

    final String TRACKER_STOPPED = "Tracker stopped";

    final String TRACKER_NOT_STARTED = "Tracker start failed";

    final String TRACKER_NOT_STOPPED = "Tracker stop failed";

    final String TRACKER_RUNNING = "Tracker is running";

    final String TRACKER_NOT_RUNNING = "Tracker is not running";

    final String BAD_EVENT = "Bad client event";

    //Torrents
    final String TORRENT_NOT_FOUND = "The Requested torrent not found";

    //Signin
    final String SIGNIN = "Signin recorded";
}
