package ga.rugal.jpt.core.service.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.entity.Announce;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
public class UserServiceImplTest extends DBTestBase
{

    @Autowired
    private UserService userService;

    @Autowired
    private User user;

    public UserServiceImplTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        userService.getDAO().save(user);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        userService.getDAO().delete(user);
    }

    @Test
    public void testAnnounce()
    {
        System.out.println("clientAnnounce");
        User bean = user;
        System.out.println(bean.getDownload());
        System.out.println(bean.getUpload());
        Announce announce = new Announce();
        announce.setDownload(100l);
        announce.setUpload(100l);
        User result = userService.announce(bean, announce);
        System.out.println(result.getDownload());
        System.out.println(result.getUpload());
    }
}
