package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.core.dao.InvitationDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Invitation;
import ga.rugal.jpt.core.entity.User;
import ml.rugal.sshcommon.page.Pagination;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
public class InvitationDaoImplTest extends JUnitSpringTestBase
{

    private Invitation bean;

    private User user;

    @Autowired
    private UserDao userDao;

    @Autowired
    private InvitationDao invitationDao;

    public InvitationDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        user = new User();
        user.setEmail("test@123.com");
        user.setLastReport(System.currentTimeMillis());
        user.setPasskey("test");
        user.setPassword("test");
        user.setRegisterTime(System.currentTimeMillis());
        user.setStatus(User.Status.DELETING);
        user.setUsername("test");
        userDao.save(user);

        bean = new Invitation();
        bean.setIssueTime(System.currentTimeMillis());
        bean.setUid(user);
        invitationDao.save(bean);

    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        //order is important
        invitationDao.deleteById(bean.getIid());
        userDao.deleteById(user.getUid());
    }

    @Test
    public void testGetPage()
    {
        System.out.println("getPage");
        int pageNo = 0;
        int pageSize = 1;
        Pagination result = invitationDao.getPage(pageNo, pageSize);
        System.out.println(result.getList().size());
    }

    @Test
    public void testGetByID()
    {
        System.out.println("getByID");
        Integer id = bean.getIid();
        Invitation expResult = bean;
        Invitation result = invitationDao.getByID(id);
        assertEquals(expResult, result);
    }

}
