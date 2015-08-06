package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.service.PostTagsService;
import ga.rugal.jpt.core.dao.PostTagsDao;
import ga.rugal.jpt.core.entity.PostTags;
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
public class PostTagsServiceImpl implements PostTagsService
{

    private static final Logger LOG = LoggerFactory.getLogger(PostTagsServiceImpl.class.getName());

    @Autowired
    private PostTagsDao dao;

    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize)
    {
        return dao.getPage(pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public PostTags findById(Integer id)
    {
        return dao.findById(id);
    }

    @Override
    public PostTags save(PostTags bean)
    {
        return dao.save(bean);
    }

    @Override
    public PostTags deleteById(Integer id)
    {
        return dao.deleteById(id);
    }

    @Override
    public PostTags update(PostTags bean)
    {
        Updater<PostTags> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

}
