package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.AdminLevelDao;
import ga.rugal.jpt.core.entity.AdminLevel;
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
public class AdminLevelDaoImpl extends HibernateBaseDao<AdminLevel, Integer> implements AdminLevelDao
{

    private static final Logger LOG = LoggerFactory.getLogger(AdminLevel.class.getName());

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
    public AdminLevel findById(Integer id)
    {
        AdminLevel entity = get(id);
        return entity;
    }

    @Override
    public AdminLevel save(AdminLevel bean)
    {
        getSession().save(bean);
        return bean;
    }

    @Override
    public AdminLevel deleteById(Integer id)
    {
        AdminLevel entity = super.get(id);
        if (entity != null)
        {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<AdminLevel> getEntityClass()
    {
        return AdminLevel.class;
    }

}
