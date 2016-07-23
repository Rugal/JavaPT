package ga.rugal.jpt.core.dao.impl;

import ga.rugal.DBTestBase;
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
public class InvitationDaoImplTest extends DBTestBase
{

    @Autowired
    private Invitation invitation;

    @Autowired
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
        userDao.save(user);
        invitationDao.save(invitation);

    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        //order is important
        invitationDao.delete(invitation);
        userDao.delete(user);
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
        Integer id = invitation.getIid();
        Invitation expResult = invitation;
        Invitation result = invitationDao.get(id);
        assertEquals(expResult, result);
    }

}
