package ga.rugal.jpt.core.service.impl;

import com.mongodb.gridfs.GridFSDBFile;
import ga.JUnitSpringTestBase;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.core.service.TorrentAccessService;
import ga.rugal.jpt.core.service.Tracker;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author Rugal Bernstein
 */
@ContextConfiguration(classes =
{
    config.MongoDBContext.class, config.TrackerContext.class, config.ApplicationContext.class
})
public class TorrentAccessServiceImplTest extends JUnitSpringTestBase
{

    @Autowired
    private TorrentAccessService torrentAccessService;

    @Autowired
    private TrackedTorrent torrent;

    @Autowired
    private Tracker tracker;

    public TorrentAccessServiceImplTest()
    {
    }

    @Before
    public void setUp() throws IOException
    {
        System.out.println("saveFile");
        torrentAccessService.saveTorrent(torrent);
    }

    @After
    public void tearDown()
    {
        System.out.println("deleteFile");
        torrentAccessService.deleteTorrent(torrent);
    }

    @Test
    public void testAnnounce() throws IOException
    {
        System.out.println("announce from MongoDB");
        List<GridFSDBFile> list = torrentAccessService.getAllTorrents();
        for (GridFSDBFile l : list)
        {
            System.out.println(l.getFilename());
            tracker.announce(TrackedTorrent.load(l));
        }

    }

}
