package config;

import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.common.tracker.common.protocol.RequestEvent;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.Announce;
import ga.rugal.jpt.core.entity.Client;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.PostTag;
import ga.rugal.jpt.core.entity.Tag;
import ga.rugal.jpt.core.entity.Thread;
import ga.rugal.jpt.core.entity.User;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Java based application context configuration class.
 * <p>
 * Including data source and transaction manager configuration.
 *
 * @author Rugal Bernstein
 * @since 0.2
 */
@Configuration
public class TestApplicationContext
{

    @Bean
    @Autowired
    public Post post(User user, Level level, TrackedTorrent torrent)
    {
        Post bean = new Post();
        bean.setContent("TEST");
        bean.setEnable(true);
        bean.setCreateTime(System.currentTimeMillis());
        bean.setSize(100);
        bean.setTitle("Test title");
        bean.setHash(torrent.getHexInfoHash());
        bean.setLevel(level);
        bean.setAuthor(user);
        bean.setRate(0);
//        bean.setBencode(torrent.getEncoded());
        return bean;
    }

    @Bean(name = "user")
    public User user()
    {
        User bean = new User();
        bean.setEmail("testhappy@128.com");
        bean.setPassword("test123");
        bean.setRegisterTime(System.currentTimeMillis());
        bean.setStatus(User.Status.VALID);
        bean.setUsername("tenjin");
        return bean;
    }

    @Autowired
    @Bean
    public PostTag postTags(Post post, Tag tag)
    {
        PostTag postTags = new PostTag();
        postTags.setPost(post);
        postTags.setTag(tag);
        return postTags;
    }

    @Bean
    public Client client()
    {
        Client bean = new Client();
        bean.setEnable(true);
        bean.setName("transmit");
        bean.setVersion("*");
        return bean;
    }

    @Autowired
    @Bean
    public Thread thread(User user, Post post)
    {
        Thread bean = new Thread();
        bean.setContent("TEST CONTENT");
        bean.setPost(post);
        bean.setCreateTime(System.currentTimeMillis());
        bean.setReplyer(user);
        return bean;
    }

    @Bean
    public Level level()
    {
        Level bean = new Level();
        bean.setUpload(0l);
        bean.setDownload(0l);
        bean.setName("Test");
        return bean;
    }

    @Bean
    public Tag tag()
    {
        Tag bean = new Tag();
        bean.setName("Test use only");
        return bean;
    }

    @Autowired
    @Bean
    public Announce announce(User user, Client client, Post post)
    {
        Announce bean = new Announce();
        bean.setAnnounceTime(System.currentTimeMillis());
        bean.setClient(client);
        bean.setUser(user);
        bean.setPost(post);
        bean.setUpload(0l);
        bean.setDownload(0l);
        return bean;
    }

    @Autowired
    @Bean
    public Admin admin(User user)
    {
        Admin bean = new Admin();
        bean.setUser(user);
        bean.setGranter(user);
        bean.setSince(System.currentTimeMillis());
        bean.setRole(Admin.Role.SUPER);
        return bean;
    }

    @Bean
    @Autowired
    public TrackerUpdateBean trackerUpdateBean(User user, Post post, Client client)
    {
        TrackerUpdateBean bean = new TrackerUpdateBean();
        bean.setClient(client);
        bean.setDownloaded(99l);
        bean.setUploaded(101l);
        bean.setLeft(100l);
        bean.setEvent(RequestEvent.STARTED);
        bean.setInfoHash(post.getHash());
        bean.setPost(post);
        bean.setUser(user);

        return bean;
    }

    @Bean
    @Autowired
    public TrackedTorrent torrent(File testTorrentFile) throws IOException
    {
        TrackedTorrent torrent = TrackedTorrent.load(testTorrentFile);
        return torrent;
    }

    @Bean
    public File testTorrentFile()
    {
        File file = new File(SystemDefaultProperties.TORRENT_PATH)
            .listFiles((File dir, String fileName) -> fileName.startsWith("Junit"))[0];
        return file;
    }
}
