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

    final String TRACKER_VERSION = "Rugal v1";

    final int PEER_COLLECTION_FREQUENCY_SECONDS = 15;
}
