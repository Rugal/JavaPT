package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.dao.SigninLogDao;
import ga.rugal.jpt.core.entity.SigninLog;
import ga.rugal.jpt.core.service.SigninLogService;
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
public class SigninLogServiceImpl implements SigninLogService
{

    private static final Logger LOG = LoggerFactory.getLogger(SigninLogServiceImpl.class.getName());

    @Autowired
    private SigninLogDao dao;

    @Override
    public SigninLogDao getDAO()
    {
        return this.dao;
    }

    @Override
    public SigninLog update(SigninLog bean)
    {
        Updater<SigninLog> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }
}
