package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.entity.Level;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.service.LevelService;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Service
@Transactional
public class LevelServiceImpl implements LevelService
{

    @Autowired
    private LevelDao dao;

    @Override
    public Level update(Level bean)
    {
        Updater<Level> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

    @Override
    public LevelDao getDAO()
    {
        return this.dao;
    }
}
