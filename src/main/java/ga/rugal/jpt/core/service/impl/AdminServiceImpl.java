package ga.rugal.jpt.core.service.impl;

import com.google.common.collect.Sets;
import ga.rugal.jpt.core.dao.AdminDao;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.AdminService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import ml.rugal.sshcommon.hibernate.Updater;
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
        //-----These comments is here for testing transactional consistency.-------
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
    public boolean meetAllAdminLevels(User user, Admin.Role... roles)
    {
        List<Admin> admins = this.getDAO().getByUID(user);
        //if contains all the roles
        return admins.stream().map(admin -> admin.getRole()).collect(Collectors.toSet())
            .containsAll(Sets.newHashSet(roles));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean meetAnyAdminLevel(User user, Admin.Role... roles)
    {
        List<Admin> admins = this.getDAO().getByUID(user);
        //flatten roles
        Set<Admin.Role> set = admins.stream().map(admin -> admin.getRole()).collect(Collectors.toSet());
        //do intersection
        set.retainAll(Sets.newHashSet(roles));
        return !set.isEmpty();//at least one role
    }
}
