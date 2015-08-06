package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.entity.Post;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import ml.rugal.sshcommon.page.Pagination;
import org.hibernate.Criteria;
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

    private static final Logger LOG = LoggerFactory.getLogger(Post.class.getName());

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
    public Post findById(Integer id)
    {
        Post entity = get(id);
        return entity;
    }

    @Override
    public Post save(Post bean)
    {
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
    protected Class<Post> getEntityClass()
    {
        return Post.class;
    }

}
