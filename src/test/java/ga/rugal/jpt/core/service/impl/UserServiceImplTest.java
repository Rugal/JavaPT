package ga.rugal.jpt.core.service.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.entity.ClientAnnounce;
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
    public void testClientAnnounce()
    {
        System.out.println("clientAnnounce");
        User bean = user;
        System.out.println(bean.getDownloadByte());
        System.out.println(bean.getUploadByte());
        ClientAnnounce clientAnnounce = new ClientAnnounce();
        clientAnnounce.setDownloadByte(100l);
        clientAnnounce.setUploadByte(100l);
        User result = userService.clientAnnounce(bean, clientAnnounce);
        System.out.println(result.getDownloadByte());
        System.out.println(result.getUploadByte());
    }

}
