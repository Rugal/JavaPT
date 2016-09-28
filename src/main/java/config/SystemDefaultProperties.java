package config;

/**
 *
 * Some system level properties.
 *
 * @author Rugal Bernstein
 */
public interface SystemDefaultProperties
{

    boolean TORRENT_FROM_DB = true;

    final String SCHEMA = "jpt";

    final String ID = "id";

    final int INVITATION_CODE_LENGTH = 15;

    final int INVITATION_CREDIT_NEED = 100;

    final String CREDENTIAL = "credential";

    final String STATIC_PATH = "static";

    final String TORRENT_PATH = "torrents";

    final String TORRENT_SUFIX = ".torrent";

    final String BYTE_ENCODING = "UTF-8";

    final String TRACKER_VERSION = "Rugal PT v1";

    final int PEER_COLLECTION_FREQUENCY_SECONDS = 15;

    final int FRESH_TIME_SECONDS = 30;

    final int MIN_REANNOUNCE_INTERVAL = 30;

    /**
     * Default number of peers included in a tracker response.
     */
    final int DEFAULT_ANSWER_NUM_PEERS = 50;

    /**
     * Default announce interval requested from peers, in seconds.
     */
    final int DEFAULT_ANNOUNCE_INTERVAL_SECONDS = 60;

    /**
     * Torrent file piece length (in bytes), we use 512 kB.
     */
    final int DEFAULT_PIECE_LENGTH = 512 * 1024;

    final int PIECE_HASH_SIZE = 20;

    final String DEFAULT_PAGE_NUMBER = "1";

    final String DEFAULT_PAGE_SIZE = "5";

    final String ICON_PATH = "icon";

    final String ANNOUNCE_TEMPLATE = "http://localhost:8080/announce?uid=%s&credential=%s";

    final String UID = "uid";

    final String BITTORRENT_MIME = "application/x-bittorrent";
}
