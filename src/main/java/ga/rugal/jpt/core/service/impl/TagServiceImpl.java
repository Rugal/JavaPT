package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.service.TagService;
import ga.rugal.jpt.core.dao.TagDao;
import ga.rugal.jpt.core.entity.Tag;
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
public class TagServiceImpl implements TagService
{

    private static final Logger LOG = LoggerFactory.getLogger(TagServiceImpl.class.getName());

    @Autowired
    private TagDao dao;

    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize)
    {
        return dao.getPage(pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public Tag getByID(Integer id)
    {
        return dao.getByID(id);
    }

    @Override
    public Tag save(Tag bean)
    {
        return dao.save(bean);
    }

    @Override
    public Tag deleteById(Integer id)
    {
        return dao.deleteById(id);
    }

    @Override
    public Tag update(Tag bean)
    {
        Updater<Tag> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

}
