package ga.rugal.jpt.core.service;

import com.mongodb.gridfs.GridFSDBFile;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Rugal Bernstein
 */
public interface TorrentAccessService
{

    TrackedTorrent deleteTorrent(TrackedTorrent torrent);

    List<GridFSDBFile> getAllTorrents();

    GridFSDBFile getTorrent(TrackedTorrent torrent);

    GridFSDBFile getTorrent(String hash);

    TrackedTorrent saveTorrent(TrackedTorrent torrent) throws IOException;

}
