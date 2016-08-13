package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.dao.PostTagsDao;
import ga.rugal.jpt.core.entity.PostTag;
import ga.rugal.jpt.core.service.PostTagsService;
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
public class PostTagsServiceImpl implements PostTagsService
{

    @Autowired
    private PostTagsDao dao;

    @Override
    public PostTagsDao getDAO()
    {
        return this.dao;
    }

    @Override
    public PostTag update(PostTag bean)
    {
        Updater<PostTag> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }
}
