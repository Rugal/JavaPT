package ga.rugal.jpt.common;

/**
 *
 * Some system level properties.
 *
 * @author Rugal Bernstein
 */
public interface SystemDefaultProperties
{

    final String ID = "id";

    final String CREDENTIAL = "credential";

    final String TORRENT_PATH = "torrents";

    final String TORRENT_SUBFIX = ".torrent";

    final String BYTE_ENCODING = "UTF-8";
//    final String BYTE_ENCODING = "ISO-8859-1";

    final int MIN_REANNOUNCE_INTERVAL = 15;

    final String TRACKER_VERSION = "Rugal PT v1";

    final int PEER_COLLECTION_FREQUENCY_SECONDS = 15;

    final int FRESH_TIME_SECONDS = 30;

    /**
     * Minimum announce interval requested from peers, in seconds.
     */
    final int MIN_ANNOUNCE_INTERVAL_SECONDS = 5;

    /**
     * Default number of peers included in a tracker response.
     */
    final int DEFAULT_ANSWER_NUM_PEERS = 50;

    /**
     * Default announce interval requested from peers, in seconds.
     */
    final int DEFAULT_ANNOUNCE_INTERVAL_SECONDS = 10;

    /**
     * Torrent file piece length (in bytes), we use 512 kB.
     */
    final int DEFAULT_PIECE_LENGTH = 512 * 1024;

    final int PIECE_HASH_SIZE = 20;

}
