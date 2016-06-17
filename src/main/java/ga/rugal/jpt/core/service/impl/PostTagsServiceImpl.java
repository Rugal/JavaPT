package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.dao.PostTagsDao;
import ga.rugal.jpt.core.entity.PostTags;
import ga.rugal.jpt.core.service.PostTagsService;
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
public class PostTagsServiceImpl implements PostTagsService
{

    private static final Logger LOG = LoggerFactory.getLogger(PostTagsServiceImpl.class.getName());

    @Autowired
    private PostTagsDao dao;

    @Override
    public PostTagsDao getDAO()
    {
        return this.dao;
    }

    @Override
    public PostTags update(PostTags bean)
    {
        Updater<PostTags> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }
}
