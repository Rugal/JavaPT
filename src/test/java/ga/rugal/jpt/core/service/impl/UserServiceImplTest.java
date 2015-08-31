package ga.rugal.jpt.core.service.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.TestApplicationContext;
import ga.rugal.jpt.core.dao.UserLevelDao;
import ga.rugal.jpt.core.entity.ClientAnnounce;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.entity.UserLevel;
import ga.rugal.jpt.core.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author Rugal Bernstein
 */
@ContextConfiguration(classes = TestApplicationContext.class)
public class UserServiceImplTest extends JUnitSpringTestBase
{

    @Autowired
    private UserLevelDao levelDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserLevel level;

    @Autowired
    private User user;

    public UserServiceImplTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        levelDao.save(level);
        userService.save(user);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        userService.deleteById(user.getUid());
        levelDao.deleteById(level.getLid());
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
