package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.service.SigninLogService;
import ga.rugal.jpt.core.dao.SigninLogDao;
import ga.rugal.jpt.core.entity.SigninLog;
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
public class SigninLogServiceImpl implements SigninLogService
{

    private static final Logger LOG = LoggerFactory.getLogger(SigninLogServiceImpl.class.getName());

    @Autowired
    private SigninLogDao dao;

    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize)
    {
        return dao.getPage(pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public SigninLog findById(Integer id)
    {
        return dao.findById(id);
    }

    @Override
    public SigninLog save(SigninLog bean)
    {
        return dao.save(bean);
    }

    @Override
    public SigninLog deleteById(Integer id)
    {
        return dao.deleteById(id);
    }

    @Override
    public SigninLog update(SigninLog bean)
    {
        Updater<SigninLog> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

}
