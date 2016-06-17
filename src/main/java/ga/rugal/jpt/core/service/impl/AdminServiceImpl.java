package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.dao.AdminDao;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.service.AdminService;
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
}
