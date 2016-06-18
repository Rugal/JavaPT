package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.dao.AdminDao;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.AdminService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ml.rugal.sshcommon.hibernate.Updater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService
{

    private static final Logger LOG = LoggerFactory.getLogger(AdminServiceImpl.class.getName());

    @Autowired
    private AdminDao dao;

    @Override
    public AdminDao getDAO()
    {
        return dao;
    }

    @Override
    public Admin update(Admin bean)
    {

        Updater<Admin> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
        //-----These comments is here for testing transaction consistency.-------
//        throw new RuntimeException();
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public boolean isAdmin(User user)
    {
        return this.dao.getByUID(user).size() > 0;
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public boolean meetAdminLevels(User user, Admin.Level... levels)
    {
        List<Admin> admins = this.getDAO().getByUID(user);
        if (admins.isEmpty())
        {
            return false;
        }
        //Deny access by default if without any required role
        boolean value = false;
        //---------------------
        Set<Admin.Level> ownedLevel = new HashSet<>(5);
        //Replace user.getAdminList as hibernate lazy loading problem
        admins.stream().forEach((admin) ->
            {
                ownedLevel.add(admin.getLevel());
            });

        for (Admin.Level requiredLevel : levels)
        {
            if (ownedLevel.contains(requiredLevel))
            {
                //If match any role
                value = true;
                break;
            }
        }
        return value;
    }

}
