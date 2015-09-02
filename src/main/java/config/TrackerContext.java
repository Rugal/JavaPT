package config;

import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.common.SystemDefaultProperties;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.common.tracker.server.Tracker;
import ga.rugal.jpt.springmvc.PackageInfo;
import ga.rugal.jpt.springmvc.controller.AnnounceAction;
import ga.rugal.jpt.springmvc.controller.TrackerAction;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 *
 * @author Rugal Bernstein
 */
@Configuration
@ComponentScan(basePackageClasses = PackageInfo.class, includeFilters
               = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes =
                                       {
                                           AnnounceAction.class, TrackerAction.class
    }))
public class TrackerContext
{

    private static final Logger LOG = LoggerFactory.getLogger(TrackerContext.class.getName());

    /**
     * Create a tracker server in local, with same port to servlet container
     * Spring will start this tracker after creation.
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
            Tracker tracker = new Tracker();
            File folder = new File(SystemDefaultProperties.TORRENT_PATH);
            LOG.debug(CommonLogContent.OPEN_TORRENT_FOLDER, folder.getAbsolutePath());
            File[] torrentFiles = folder.listFiles((File dir, String fileName) -> fileName.endsWith(SystemDefaultProperties.TORRENT_SUBFIX));
            if (null != torrentFiles && torrentFiles.length != 0)
            {
                for (File torrentFile : torrentFiles)
                {
                    TrackedTorrent torrent = TrackedTorrent.load(torrentFile);
                    tracker.announce(torrent);
                }
                LOG.info(CommonLogContent.TRACKER_CREATED, torrentFiles.length);
            }
            else
            {
                LOG.info(CommonLogContent.TRACKER_NO_TORRENT);
            }
            return tracker;
        }
        catch (Exception e)
        {
            throw new Exception(CommonLogContent.TRACKER_NOT_CREATED, e);
        }
    }

}
