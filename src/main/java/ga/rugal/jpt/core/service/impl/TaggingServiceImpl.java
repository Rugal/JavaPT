package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.entity.Tagging;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ga.rugal.jpt.core.service.TaggingService;
import ga.rugal.jpt.core.dao.TaggingDao;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Service
@Transactional
public class TaggingServiceImpl implements TaggingService
{

    @Autowired
    private TaggingDao dao;

    @Override
    public TaggingDao getDAO()
    {
        return this.dao;
    }

    @Override
    public Tagging update(Tagging bean)
    {
        Updater<Tagging> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }
}
