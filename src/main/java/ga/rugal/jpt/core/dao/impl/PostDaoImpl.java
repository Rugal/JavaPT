package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.entity.Post;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Repository
public class PostDaoImpl extends HibernateBaseDao<Post, Integer> implements PostDao
{

    @Override
    @Transactional(readOnly = true)
    public Post getByTorrent(String infoHash)
    {
        return this.findUniqueByProperty("infoHash", infoHash);
    }

    @Override
    @Transactional(readOnly = true)
    public List getAllTorrentsOnly()
    {
        Criteria crit = createCriteria();
        crit.setProjection(Projections.property("bencode"));
        return crit.list();
    }

    @Override
    protected Class<Post> getEntityClass()
    {
        return Post.class;
    }
}
