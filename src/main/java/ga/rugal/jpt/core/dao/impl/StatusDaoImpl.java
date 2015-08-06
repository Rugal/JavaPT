package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.StatusDao;
import ga.rugal.jpt.core.entity.Status;
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
public class StatusDaoImpl extends HibernateBaseDao<Status, Integer> implements StatusDao
{

    private static final Logger LOG = LoggerFactory.getLogger(Status.class.getName());

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
    public Status findById(Integer id)
    {
        Status entity = get(id);
        return entity;
    }

    @Override
    public Status save(Status bean)
    {
        getSession().save(bean);
        return bean;
    }

    @Override
    public Status deleteById(Integer id)
    {
        Status entity = super.get(id);
        if (entity != null)
        {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<Status> getEntityClass()
    {
        return Status.class;
    }

}
