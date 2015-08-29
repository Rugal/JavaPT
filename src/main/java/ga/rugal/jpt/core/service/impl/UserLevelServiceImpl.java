package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.service.UserLevelService;
import ga.rugal.jpt.core.dao.UserLevelDao;
import ga.rugal.jpt.core.entity.UserLevel;
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
public class UserLevelServiceImpl implements UserLevelService
{

    private static final Logger LOG = LoggerFactory.getLogger(UserLevelServiceImpl.class.getName());

    @Autowired
    private UserLevelDao dao;

    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize)
    {
        return dao.getPage(pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public UserLevel getByID(Integer id)
    {
        return dao.getByID(id);
    }

    @Override
    public UserLevel save(UserLevel bean)
    {
        return dao.save(bean);
    }

    @Override
    public UserLevel deleteById(Integer id)
    {
        return dao.deleteById(id);
    }

    @Override
    public UserLevel update(UserLevel bean)
    {
        Updater<UserLevel> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

}