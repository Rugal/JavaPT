package ga.rugal.jpt.core.service.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.common.SystemDefaultProperties;
import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.common.tracker.server.TrackedPeer;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.core.service.Tracker;
import java.io.File;
import java.io.IOException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author Rugal Bernstein
 */
@ContextConfiguration(classes = config.TrackerContext.class)
public class TrackerImplTest extends DBTestBase
{

    @Autowired
    private Tracker tracker;

    public TrackerImplTest()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    @Ignore
    public void testUpdate() throws Exception
    {
        System.out.println("update");
        TrackerUpdateBean bean = null;
        TrackerImpl instance = new TrackerImpl();
        TrackedPeer expResult = null;
        TrackedPeer result = instance.update(bean);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testAnnounce() throws IOException
    {
        File folder = new File(SystemDefaultProperties.TORRENT_PATH);

        File[] torrentFiles = folder.listFiles((File dir, String fileName) -> fileName.endsWith(SystemDefaultProperties.TORRENT_SUBFIX));
        if (null != torrentFiles && torrentFiles.length != 0)
        {
            for (File torrentFile : torrentFiles)
            {
                TrackedTorrent torrent = TrackedTorrent.load(torrentFile);
                tracker.announce(torrent);
            }
        }
    }

}
