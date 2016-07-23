package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.common.tracker.common.Torrent;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.service.PostService;
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
public class PostServiceImpl implements PostService
{

    @Autowired
    private PostDao dao;

    @Override
    public PostDao getDAO()
    {
        return this.dao;
    }

    @Override
    public Post save(Post bean, Torrent torrent)
    {
        bean.setBencode(torrent.getEncoded());
        return dao.save(bean);
    }

    @Override
    public Post update(Post bean)
    {
        Updater<Post> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

}
