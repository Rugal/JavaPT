package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.dao.SigninLogDao;
import ga.rugal.jpt.core.entity.SigninLog;
import ga.rugal.jpt.core.service.SigninLogService;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Service
@Transactional
public class SigninLogServiceImpl implements SigninLogService
{

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
