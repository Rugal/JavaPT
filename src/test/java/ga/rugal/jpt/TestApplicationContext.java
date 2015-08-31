package ga.rugal.jpt;

import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.common.tracker.common.protocol.RequestEvent;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.Client;
import ga.rugal.jpt.core.entity.ClientAnnounce;
import ga.rugal.jpt.core.entity.Invitation;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.PostTags;
import ga.rugal.jpt.core.entity.SigninLog;
import ga.rugal.jpt.core.entity.Tag;
import ga.rugal.jpt.core.entity.Thread;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.entity.UserLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(TestApplicationContext.class.getName());

    @Bean
    @Autowired
    public SigninLog signinLog(User user)
    {
        SigninLog bean = new SigninLog();
        bean.setIp("127.0.0.1");
        bean.setSigninTime(System.currentTimeMillis());
        bean.setUid(user);
        return bean;
    }

    @Bean
    @Autowired
    public Invitation invitation(User user)
    {
        Invitation bean = new Invitation();
        bean.setIssueTime(System.currentTimeMillis());
        bean.setUid(user);
        return bean;
    }

    @Bean
    @Autowired
    public Post post(User user, UserLevel level)
    {
        Post bean = new Post();
        bean.setContent("TEST");
        bean.setEnabled(true);
        bean.setPostTime(System.currentTimeMillis());
        bean.setSize(100);
        bean.setTitle("Test title");
        bean.setTorrentHash("111111111111");
        bean.setMinLevel(level);
        bean.setUid(user);
        bean.setRate(0);
        return bean;
    }

    @Bean
    public User user()
    {
        User bean = new User();
        bean.setEmail("test@123.com");
        bean.setPassword("test");
        bean.setRegisterTime(System.currentTimeMillis());
        bean.setStatus(User.Status.DELETING);
        bean.setUsername("test");
        return bean;
    }

    @Autowired
    @Bean
    public PostTags postTags(Post post, Tag tag)
    {
        PostTags postTags = new PostTags();
        postTags.setPid(post);
        postTags.setTid(tag);
        return postTags;
    }

    @Bean
    public Client client()
    {
        Client bean = new Client();
        bean.setEnabled(true);
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
        bean.setPid(post);
        bean.setPostTime(System.currentTimeMillis());
        bean.setUid(user);
        return bean;
    }

    @Bean
    public UserLevel level()
    {
        UserLevel bean = new UserLevel();
        bean.setMinimum(Integer.MAX_VALUE);
        bean.setName("Test");
        return bean;
    }

    @Bean
    public Tag tag()
    {
        Tag bean = new Tag();
        bean.setIcon("icon/test.jpg");
        bean.setName("Test use only");
        return bean;
    }

    @Autowired
    @Bean
    public ClientAnnounce clientAnnounce(User user, Client client, Post post)
    {
        ClientAnnounce bean = new ClientAnnounce();
        bean.setAnnounceTime(System.currentTimeMillis());
        bean.setCid(client);
        bean.setUid(user);
        bean.setTorrentPost(post);
        bean.setUploadByte(0l);
        bean.setDownloadByte(0l);
        return bean;
    }

    @Autowired
    @Bean
    public Admin admin(User user)
    {
        Admin bean = new Admin();
        bean.setUid(user);
        bean.setGrantee(user);
        bean.setSince(System.currentTimeMillis());
        bean.setLevel(Admin.Level.ADMIN);
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
        bean.setInfoHash(post.getTorrentHash());
        bean.setPost(post);
        bean.setUser(user);

        return bean;
    }

}
