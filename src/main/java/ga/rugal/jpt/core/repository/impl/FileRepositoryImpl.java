package ga.rugal.jpt.core.repository.impl;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.core.repository.FileRepository;
import java.io.FileInputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Rugal Bernstein
 */
@Repository
public class FileRepositoryImpl implements FileRepository
{

    private static final Logger LOG = LoggerFactory.getLogger(FileRepositoryImpl.class.getName());

    @Autowired
    private GridFS gridFS;

    @Override
    public TrackedTorrent saveFile(TrackedTorrent torrent) throws IOException
    {
        FileInputStream in = new FileInputStream(torrent.getTorrentFile());
        GridFSInputFile gfsFile = gridFS.createFile(in, torrent.getHexInfoHash(), true);
        gfsFile.save();
        return torrent;
    }

    @Override
    public TrackedTorrent deleteFile(TrackedTorrent torrent)
    {
        gridFS.remove(gridFS.findOne(torrent.getHexInfoHash()));
        return torrent;
    }

    @Override
    public GridFSDBFile getFile(TrackedTorrent torrent)
    {
        return gridFS.findOne(torrent.getHexInfoHash());
    }
}
