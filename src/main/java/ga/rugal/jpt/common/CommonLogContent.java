package ga.rugal.jpt.common;

/**
 *
 * @author rugal
 */
public interface CommonLogContent
{

    //User
    final String USE_INVITATION_CODE = "Invitation code [{}] used for inviting user [{}]";

    //Access authentication
    final String USER_TRY_ACCESS = "User [{0}] is trying to access [{1}] from host [{2}]";

    final String USER_ACCESS_FAILED = "User [{0}] with credential [{1}] failed to access [{2}] from host [{3}]";

    final String USER_ACCESS_SUCCEEDED = "User [{0}] access [{1}] from host [{2}] succeeded";

    //Role authority
    final String USER_ROLE_ACCESS = "User [{0}] is trying to access handler [{1}]";

    final String USER_ROLE_FAILED = "User [{0}] is denied to access handler [{1}]";

    final String USER_ROLE_SUCCEEDED = "User [{0}] is allowed to access handler [{1}]";

    //Tracker operations
    final String SUPER_START_TRACKER = "Superuser [{}] is starting tracker server from host [{}]";

    final String TRACKER_STARTED = "Tracker server is started by superuser [{}]";

    final String TRACKER_NOT_STARTED = "Unable to start tracker server by superuser [{0}]";

    final String SUPER_STOP_TRACKER = "Superuser [{}] is stoping tracker server from host [{}]";

    final String TRACKER_STOPPED = "Tracker server is stopped by superuser [{}]";

    final String TRACKER_NOT_STOPPED = "Unable to stop tracker server by superuser [{0}]";

    final String TRACKER_CREATED = "Tracker created with {} torrent(s)";

    final String TRACKER_NO_TORRENT = "Tracker created without announcing any torrent";

    final String TRACKER_NOT_CREATED = "Unable to create tracker";

    final String OPEN_TORRENT_FOLDER = "Opening torrents folder [{}]";

    final String COLLECT_PEERS = "Collecting peers";

    final String ERROR_WRITING_RESPONSE = "Exception occurs when writing bytes into response";

    //Torrent
    final String TORRENT_ANNOUNCED = "Torrent [{}] already announced with hash {}.";

    final String TORRENT_NEW = "New torrent [{}] registered with hash {}.";

    final String TORRENT_DELETE = "Torrent [{}] with hash {} is deleted.";

    final String TORRENT_UPLOADED = "Torrent file for post [{}] has been uploaded by user [{}]";

    //Peer
    final String STALE_PEERS = "Collecting stale peer {}:";

    //Signin
    final String SIGNIN = "User [{0}] signin from host [{1}].";

    //Announce
    final String INVALID_UID = "Host [{}] fired invalid format of uid";

    final String INVALID_CREDENTIAL = "Host [{}] fired invalid format of credential";

    final String THE_REQUESTED_INFO = "UID [{}] is requesting hash [{}]";

    final String CLIENT_VERSION_NOT_FOUND = "Client [{}] version [{}] is not found";

    final String CLIENT_NOT_FOUND = "Client [{}] is not found";

    final String OTHER_CLIENT = "Any other client softwares is {} by default";

    final String BAD_EVENT = "UID [{}] report with bad event [{}]";

    final String UPDATE_CONTENT = "UID [{0}] report [{1}] with download [{2}}, upload [{3}], left [{4}] from IP [{5}}";

    final String START_GENERATE = "Start to generate corresponding tracker update bean";

    final String START_UPDATE = "Start to update for UID [{}]";

    final String MAKE_RESPONSE = "Making HTTP response for uid [{}]";

    final String FRAUD_REQUEST = "IP [{0}] is making fraud request against UID [{1}] with hash [{2}]";

    //Tag
    final String TAG_NOT_SAVED = "Unable to save Tag icon";

    final String TAG_ICON_NOT_FOUND = "No icon found for this tag";

    final String ERROR_READ_TAG = "I/O exception occurs when reading icon";

    final String ERROR_WRITE_TAG = "I/O exception occurs when writing icon";

    final String IMAGE_LENGTH = "Length of byte array is {}";

    //Post
    final String POST_DELETE = "Post [%s] and its attached threads have been deleted";
}
