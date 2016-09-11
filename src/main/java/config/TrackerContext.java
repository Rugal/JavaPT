package config;

import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.core.service.PostService;
import ga.rugal.jpt.core.service.Tracker;
import ga.rugal.jpt.springmvc.PackageInfo;
import ga.rugal.jpt.springmvc.controller.AnnounceAction;
import ga.rugal.jpt.springmvc.controller.TrackerAction;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Configuration
@ComponentScan(basePackageClasses = PackageInfo.class, includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes =
{
    AnnounceAction.class, TrackerAction.class
}))
public class TrackerContext
{

    @Autowired
    private PostService postService;

    @Autowired
    private Tracker tracker;

    /**
     * Create a tracker server in local, with same port to servlet container Spring will start this tracker after
     * creation of bean.
     *
     * @return
     *
     * @throws java.io.IOException
     *
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Tracker tracker() throws Exception
    {
        try
        {
            if (SystemDefaultProperties.TORRENT_FROM_DB)
            {
                torrentFromDB(tracker);
            } else
            {
                torrentFromFS(tracker);
            }
            return tracker;
        }
        catch (Exception e)
        {
            throw new Exception(CommonLogContent.TRACKER_NOT_CREATED, e);
        }
    }

    private void torrentFromDB(Tracker tracker) throws IOException
    {
        List<Object> list = postService.getDAO().getAllTorrentsOnly();
        if (null != list && !list.isEmpty())
        {
            for (Object bencode : list)
            {
                TrackedTorrent torrent = TrackedTorrent.load((byte[]) bencode);
                tracker.torrontRegister(torrent);
            }
            LOG.info(CommonLogContent.TRACKER_CREATED, list.size());
        } else
        {
            LOG.info(CommonLogContent.TRACKER_NO_TORRENT);
        }
    }

    private void torrentFromFS(Tracker tracker) throws IOException
    {
        File folder = new File(SystemDefaultProperties.TORRENT_PATH);
        LOG.debug(CommonLogContent.OPEN_TORRENT_FOLDER, folder.getAbsolutePath());
        File[] torrentFiles = folder.listFiles((File dir, String fileName) -> fileName.endsWith(SystemDefaultProperties.TORRENT_SUFIX));
        if (null != torrentFiles && torrentFiles.length != 0)
        {
            for (File torrentFile : torrentFiles)
            {
                TrackedTorrent torrent = TrackedTorrent.load(torrentFile);
                tracker.torrontRegister(torrent);
            }
            LOG.info(CommonLogContent.TRACKER_CREATED, torrentFiles.length);
        } else
        {
            LOG.info(CommonLogContent.TRACKER_NO_TORRENT);
        }
    }
}
