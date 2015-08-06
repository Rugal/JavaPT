package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.service.AdminService;
import ga.rugal.jpt.core.dao.AdminDao;
import ga.rugal.jpt.core.entity.Admin;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
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
    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize)
    {
        return dao.getPage(pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public Admin findById(Integer id)
    {
        return dao.findById(id);
    }

    @Override
    public Admin save(Admin bean)
    {
        return dao.save(bean);
    }

    @Override
    public Admin deleteById(Integer id)
    {
        return dao.deleteById(id);
    }

    @Override
    public Admin update(Admin bean)
    {
        Updater<Admin> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

}
