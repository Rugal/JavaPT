package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.common.tracker.common.Torrent;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.service.PostService;
import java.util.List;
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
public class PostServiceImpl implements PostService
{

    private static final Logger LOG = LoggerFactory.getLogger(PostServiceImpl.class.getName());

    @Autowired
    private PostDao dao;

    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize)
    {
        return dao.getPage(pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public Post getByID(Integer id)
    {
        return dao.getByID(id);
    }

    @Override
    public Post save(Post bean)
    {
        return dao.save(bean);
    }

    @Override
    public Post save(Post bean, Torrent torrent)
    {
        bean.setBencode(torrent.getEncoded());
        return dao.save(bean);
    }

    @Override
    public Post deleteById(Integer id)
    {
        return dao.deleteById(id);
    }

    @Override
    public Post update(Post bean)
    {
        Updater<Post> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

    @Transactional(readOnly = true)
    @Override
    public Post getByTorrent(String infoHash)
    {
        return dao.getByTorrent(infoHash);
    }

    @Override
    @Transactional(readOnly = true)
    public List getAllTorrentsOnly()
    {
        return dao.getAllTorrentsOnly();
    }

}
