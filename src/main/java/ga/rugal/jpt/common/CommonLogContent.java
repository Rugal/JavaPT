package ga.rugal.jpt.common;

/**
 *
 * @author rugal
 */
public interface CommonLogContent
{

    //Access authentication
    String USER_TRY_ACCESS = "User [{0}] is trying to access [{1}] from host [{2}]";

    String USER_ACCESS_FAILED = "User [{0}] with credential [{1}] failed to access [{2}] from host [{3}]";

    String USER_ACCESS_SUCCEEDED = "User [{0}] access [{1}] from host [{2}] succeeded";

    //Role authority
    String USER_ROLE_ACCESS = "User [{0}] is trying to access handler [{1}]";

    String USER_ROLE_FAILED = "User [{0}] is denied to access handler [{1}]";

    String USER_ROLE_SUCCEEDED = "User [{0}] is allowed to access handler [{1}]";

    //Tracker operations
    String SUPER_START_TRACKER = "Superuser [{0}] is starting tracker server from host [{1}]";

    String TRACKER_STARTED = "Tracker server is started by superuser [{0}]";

    String TRACKER_NOT_STARTED = "Unable to start tracker server by superuser [{0}]";

    String SUPER_STOP_TRACKER = "Superuser [{0}] is stoping tracker server from host [{1}]";

    String TRACKER_STOPPED = "Tracker server is stopped by superuser [{0}]";

    String TRACKER_NOT_STOPPED = "Unable to stop tracker server by superuser [{0}]";
}
