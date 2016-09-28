package ga.rugal.jpt.core.service.impl;

import config.SystemDefaultProperties;
import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Invitation;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.InvitationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Administrator
 */
@Slf4j
public class InvitationServiceImplTest extends DBTestBase
{

    private Invitation invitation;

    @Autowired
    private User user;

    @Autowired
    private UserDao userDao;

    @Autowired
    private InvitationService invitationService;

    public InvitationServiceImplTest()
    {
    }

    @Before
    public void setUp()
    {
        LOG.info("setUp");
        userDao.save(user);
        invitation = invitationService.generate(user);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        invitationService.getDAO().delete(invitation);
        userDao.delete(user);
    }

    @Test
    public void consume()
    {
        Assert.assertTrue(invitationService.getDAO().isUsable(user, invitation.getId()));
        invitationService.consume(user, invitation);
        Assert.assertFalse(invitationService.getDAO().isUsable(user, invitation.getId()));
    }

    @Test
    public void generate_fail()
    {
        user.setCredit(0);
        Assert.assertNull(invitationService.generate(user));
        user.setCredit(SystemDefaultProperties.INVITATION_CREDIT_NEED);
    }
}
