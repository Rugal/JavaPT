package ga.rugal.jpt.core.dao;

import com.mongodb.gridfs.GridFSDBFile;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Rugal Bernstein
 */
public interface FileRepository
{

    TrackedTorrent deleteFile(TrackedTorrent torrent);

    GridFSDBFile getFile(TrackedTorrent torrent);

    GridFSDBFile getFile(String hash);

    TrackedTorrent saveFile(TrackedTorrent torrent) throws IOException;

    List<GridFSDBFile> getAllFiles();

}
