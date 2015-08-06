package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.PostTagsDao;
import ga.rugal.jpt.core.entity.PostTags;
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
public class PostTagsDaoImpl extends HibernateBaseDao<PostTags, Integer> implements PostTagsDao
{

    private static final Logger LOG = LoggerFactory.getLogger(PostTagsDaoImpl.class.getName());

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
    public PostTags getByID(Integer id)
    {
        PostTags entity = get(id);
        return entity;
    }

    @Override
    public PostTags save(PostTags bean)
    {
        getSession().save(bean);
        return bean;
    }

    @Override
    public PostTags deleteById(Integer id)
    {
        PostTags entity = super.get(id);
        if (entity != null)
        {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<PostTags> getEntityClass()
    {
        return PostTags.class;
    }

}
