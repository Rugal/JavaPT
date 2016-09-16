package ga.rugal.jpt.core.service.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.entity.Announce;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.UserService;
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
        LOG.info("setUp");
        userService.getDAO().save(user);

    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        userService.getDAO().delete(user);
    }

    @Test
    public void announce()
    {
        Announce announce = new Announce();
        announce.setDownload(100l);
        announce.setUpload(100l);
        long download = user.getDownload(), upload = user.getUpload();
        userService.announce(user, announce);
        Assert.assertNotSame(download, user.getDownload());
        Assert.assertNotSame(upload, user.getUpload());
    }

    @Test
    public void update()
    {
        Integer credit = 32123;
        User db = userService.getDAO().get(user.getUid());
        db.setCredit(credit);
        userService.update(db);
        db = userService.getDAO().get(user.getUid());
        Assert.assertEquals(credit, db.getCredit());
    }
}
