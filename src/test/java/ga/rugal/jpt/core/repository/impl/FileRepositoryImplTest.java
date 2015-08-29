package ga.rugal.jpt.core.repository.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author Rugal Bernstein
 */
@ContextConfiguration(classes = config.MongoDBContext.class)
public class FileRepositoryImplTest extends JUnitSpringTestBase
{

    @Autowired
    private FileRepositoryImpl repository;

    private TrackedTorrent torrent;

    public FileRepositoryImplTest()
    {
    }

    @Before
    public void setUp() throws IOException
    {
        File file = new File("E:\\WorkSpaces\\Coding\\JavaPT\\torrents\\5C84616F2E28D03BF9C127D7BCCAA4CF0FD57B43.torrent");
        torrent = TrackedTorrent.load(file);
    }

    @After
    public void tearDown()
    {
    }

    @Test
    @Ignore
    public void testSaveFile() throws Exception
    {
        System.out.println("saveFile");
        repository.saveFile(torrent);
    }

    @Test
    @Ignore
    public void testDeleteFile()
    {
        System.out.println("deleteFile");
        repository.deleteFile(torrent);
    }

}
