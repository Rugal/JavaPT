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
    String SAVE_USER = "User registered";

    String UPDATE_USER = "User profile updated";

    String DELETE_USER = "User deleted";

    String GET_USER = "Get user profile";

    //Authentication and Authorization
    String ACCESS_FORBIDDEN = "Unmatched combination of username and credential";

    String PERMISSION_DENIED = "Insufficient permission";

    //Tracker operations
    String TRACKER_STARTED = "Tracker started";

    String TRACKER_STOPPED = "Track stopped";

    String TRACKER_NOT_STARTED = "Tracker start failed";

    String TRACKER_NOT_STOPPED = "Track stop failed";

    String TRACKER_RUNNING = "Tracker is running";

    String TRACKER_NOT_RUNNING = "Tracker is not running";
}
