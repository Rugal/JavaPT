package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.dao.TagDao;
import ga.rugal.jpt.core.entity.Tag;
import ga.rugal.jpt.core.service.TagService;
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
public class TagServiceImpl implements TagService
{

    private static final Logger LOG = LoggerFactory.getLogger(TagServiceImpl.class.getName());

    @Autowired
    private TagDao dao;

    @Override
    public TagDao getDAO()
    {
        return this.dao;
    }

    @Override
    public Tag update(Tag bean)
    {
        Updater<Tag> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }
}
