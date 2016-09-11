package ga.rugal.jpt.core.dao.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.InvitationDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Invitation;
import ga.rugal.jpt.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
public class InvitationDaoImplTest extends DBTestBase
{

    @Autowired
    private User user;

    @Autowired
    private Invitation invitation;

    @Autowired
    private UserDao userDao;

    @Autowired
    private InvitationDao invitationDao;

    @Before
    public void setUp()
    {
        LOG.info("setUp");
        userDao.save(user);
        invitationDao.save(invitation);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        invitationDao.delete(invitation);
        userDao.delete(user);
    }

    @Test
    public void get()
    {
        Invitation bean = invitationDao.get(invitation.getId());
        Assert.assertEquals(invitation, bean);
    }
}
