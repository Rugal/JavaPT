package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.dao.ThreadDao;
import ga.rugal.jpt.core.entity.Thread;
import ga.rugal.jpt.core.service.ThreadService;
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
public class ThreadServiceImpl implements ThreadService
{

    @Autowired
    private ThreadDao dao;

    @Override
    public ThreadDao getDAO()
    {
        return this.dao;
    }

    @Override
    public Thread update(Thread bean)
    {
        Updater<Thread> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }
}
