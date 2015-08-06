package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.service.ThreadService;
import ga.rugal.jpt.core.dao.ThreadDao;
import ga.rugal.jpt.core.entity.Thread;
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
public class ThreadServiceImpl implements ThreadService
{

    private static final Logger LOG = LoggerFactory.getLogger(ThreadServiceImpl.class.getName());

    @Autowired
    private ThreadDao dao;

    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize)
    {
        return dao.getPage(pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public Thread findById(Integer id)
    {
        return dao.findById(id);
    }

    @Override
    public Thread save(Thread bean)
    {
        return dao.save(bean);
    }

    @Override
    public Thread deleteById(Integer id)
    {
        return dao.deleteById(id);
    }

    @Override
    public Thread update(Thread bean)
    {
        Updater<Thread> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

}
