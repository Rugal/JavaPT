package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.dao.UserLevelDao;
import ga.rugal.jpt.core.entity.UserLevel;
import ga.rugal.jpt.core.service.UserLevelService;
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
public class UserLevelServiceImpl implements UserLevelService
{

    private static final Logger LOG = LoggerFactory.getLogger(UserLevelServiceImpl.class.getName());

    @Autowired
    private UserLevelDao dao;

    @Override
    public UserLevel update(UserLevel bean)
    {
        Updater<UserLevel> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

    @Override
    public UserLevelDao getDAO()
    {
        return this.dao;
    }

}
