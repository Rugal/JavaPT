package ga.rugal.jpt.core.dao.impl;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.core.dao.FileRepository;
import ga.rugal.jpt.springmvc.annotation.MongoDB;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Rugal Bernstein
 */
@Repository
@MongoDB
public class FileRepositoryImpl implements FileRepository
{

    private static final String FILENAME = "filename";

    private static final String MIME = "application/x-bittorrent";

    private static final Logger LOG = LoggerFactory.getLogger(FileRepositoryImpl.class.getName());

    @Autowired
    private GridFS gridFS;

    @Override
    public TrackedTorrent saveFile(TrackedTorrent torrent) throws IOException
    {
//        DBObject metaData = new BasicDBObject();
//        metaData.put("extra1", "anything 1");
//        metaData.put("extra2", "anything 2");
        ByteArrayInputStream bais = new ByteArrayInputStream(torrent.getEncoded());
        GridFSInputFile gfsFile = gridFS.createFile(bais, torrent.getHexInfoHash(), true);
        gfsFile.setContentType(MIME);
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
        return this.getFile(torrent.getHexInfoHash());
    }

    @Override
    public GridFSDBFile getFile(String hash)
    {
        return gridFS.findOne(hash);
    }

    @Override
    public List<GridFSDBFile> getAllFiles()
    {
        DBCursor cursor = gridFS.getFileList();
        List<GridFSDBFile> list = new ArrayList<>();
        while (cursor.hasNext())
        {
            DBObject object = cursor.next();
            String filename = (String) object.get(FILENAME);
            list.add(this.getFile(filename));
        }
        return list;
    }
}
