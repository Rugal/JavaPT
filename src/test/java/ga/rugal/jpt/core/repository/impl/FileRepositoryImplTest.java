package ga.rugal.jpt.core.repository.impl;

import com.mongodb.gridfs.GridFSDBFile;
import ga.JUnitSpringTestBase;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.core.repository.FileRepository;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
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
    config.MongoDBContext.class, config.ApplicationContext.class
})
public class FileRepositoryImplTest extends JUnitSpringTestBase
{

    @Autowired
    private FileRepository repository;

    @Autowired
    private TrackedTorrent torrent;

    public FileRepositoryImplTest()
    {
    }

    @Before
    public void setUp() throws IOException
    {
        System.out.println("saveFile");
        repository.saveFile(torrent);
    }

    @After
    public void tearDown()
    {
        System.out.println("deleteFile");
        repository.deleteFile(torrent);
    }

    @Test
    public void testGetFile()
    {
        System.out.println("getFile");
        GridFSDBFile result = repository.getFile(torrent);
        Assert.assertEquals(result.getFilename(), torrent.getHexInfoHash());
    }

    @Test
    public void testGetAllFiles() throws IOException
    {
        System.out.println("getAll");
        List<GridFSDBFile> list = repository.getAllFiles();
        for (GridFSDBFile l : list)
        {
            File file = new File(l.getFilename());
            System.out.println("Before write");
            Assert.assertFalse(file.exists());
            l.writeTo(file);
            System.out.println("After write");
            Assert.assertTrue(file.exists());
            file.delete();
        }
    }

}
