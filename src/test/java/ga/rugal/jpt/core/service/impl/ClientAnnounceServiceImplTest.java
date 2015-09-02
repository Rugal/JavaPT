package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.DBTestBase;
import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.core.dao.UserLevelDao;
import ga.rugal.jpt.core.entity.Client;
import ga.rugal.jpt.core.entity.ClientAnnounce;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.entity.UserLevel;
import ga.rugal.jpt.core.service.ClientAnnounceService;
import ga.rugal.jpt.core.service.ClientService;
import ga.rugal.jpt.core.service.PostService;
import ga.rugal.jpt.core.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
public class ClientAnnounceServiceImplTest extends DBTestBase
{

    @Autowired
    private UserLevelDao levelDao;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientAnnounceService clientAnnounceService;

    @Autowired
    private UserLevel level;

    @Autowired
    private TrackerUpdateBean trackerUpdateBean;

    @Autowired
    private User user;

    @Autowired
    private Post post;

    @Autowired
    private Client client;

    @Autowired
    private ClientAnnounce clientAnnounce;

    public ClientAnnounceServiceImplTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        clientService.save(client);
        levelDao.save(level);
        userService.save(user);
        postService.save(post);
        clientAnnounceService.save(clientAnnounce);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        clientAnnounceService.deleteById(clientAnnounce.getCaid());
        postService.deleteById(post.getPid());
        userService.deleteById(user.getUid());
        levelDao.deleteById(level.getLid());
        clientService.deleteById(client.getCid());
    }

    @Test
    public void testAnnounce()
    {
        System.out.println("announce");
        TrackerUpdateBean bean = trackerUpdateBean;
        User before = userService.getByID(user.getUid());
        System.out.println(before.getDownloadByte());
        System.out.println(before.getUploadByte());
        ClientAnnounce newer = clientAnnounceService.announce(bean);

        User after = userService.getByID(user.getUid());
        System.out.println(after.getDownloadByte());
        System.out.println(after.getUploadByte());

        clientAnnounceService.deleteById(newer.getCaid());
    }

}
