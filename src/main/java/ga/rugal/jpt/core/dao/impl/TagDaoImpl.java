package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.TagDao;
import ga.rugal.jpt.core.entity.Tag;
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
public class TagDaoImpl extends HibernateBaseDao<Tag, Integer> implements TagDao
{

    private static final Logger LOG = LoggerFactory.getLogger(TagDaoImpl.class.getName());

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
    public Tag getByID(Integer id)
    {
        Tag entity = get(id);
        return entity;
    }

    @Override
    public Tag save(Tag bean)
    {
        getSession().save(bean);
        return bean;
    }

    @Override
    public Tag deleteById(Integer id)
    {
        Tag entity = super.get(id);
        if (entity != null)
        {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<Tag> getEntityClass()
    {
        return Tag.class;
    }

}
