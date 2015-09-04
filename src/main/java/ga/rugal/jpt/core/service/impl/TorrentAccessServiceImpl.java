package ga.rugal.jpt.core.service.impl;

import com.mongodb.gridfs.GridFSDBFile;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.core.dao.FileRepository;
import ga.rugal.jpt.core.service.TorrentAccessService;
import ga.rugal.jpt.springmvc.annotation.MongoDB;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rugal Bernstein
 */
@Service
@MongoDB
public class TorrentAccessServiceImpl implements TorrentAccessService
{

    @Autowired
    private FileRepository repository;

    @Override
    public TrackedTorrent saveTorrent(TrackedTorrent torrent) throws IOException
    {
        return repository.saveFile(torrent);
    }

    @Override
    public TrackedTorrent deleteTorrent(TrackedTorrent torrent)
    {
        repository.deleteFile(torrent);
        return torrent;
    }

    @Override
    public GridFSDBFile getTorrent(TrackedTorrent torrent)
    {
        return repository.getFile(torrent);
    }

    @Override
    public GridFSDBFile getTorrent(String hash)
    {
        return repository.getFile(hash);
    }

    @Override
    public List<GridFSDBFile> getAllTorrents()
    {
        return repository.getAllFiles();
    }
}
