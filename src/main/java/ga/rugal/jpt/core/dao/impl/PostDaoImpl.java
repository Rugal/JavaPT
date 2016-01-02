package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.entity.Post;
import java.util.List;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import ml.rugal.sshcommon.page.Pagination;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Repository
public class PostDaoImpl extends HibernateBaseDao<Post, Integer> implements PostDao
{

    private static final Logger LOG = LoggerFactory.getLogger(PostDaoImpl.class.getName());

    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize)
    {
        Criteria crit = createCriteria();
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public Post getByID(Integer id)
    {
        Post entity = get(id);
        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public Post getByTorrent(String infoHash)
    {
        return this.findUniqueByProperty("infoHash", infoHash);
    }

    @Override
    public Post save(Post bean)
    {
        bean.setPostTime(System.currentTimeMillis());
        getSession().save(bean);
        return bean;
    }

    @Override
    public Post deleteById(Integer id)
    {
        Post entity = super.get(id);
        if (entity != null)
        {
            getSession().delete(entity);
        }
        return entity;
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
