package ga.rugal.jpt.core.repository;

import com.mongodb.gridfs.GridFSDBFile;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import java.io.IOException;

/**
 *
 * @author Rugal Bernstein
 */
public interface FileRepository
{

    TrackedTorrent deleteFile(TrackedTorrent torrent);

    GridFSDBFile getFile(TrackedTorrent torrent);

    TrackedTorrent saveFile(TrackedTorrent torrent) throws IOException;

}
