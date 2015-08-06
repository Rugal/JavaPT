package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.AdminDao;
import ga.rugal.jpt.core.entity.Admin;
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
public class AdminDaoImpl extends HibernateBaseDao<Admin, Integer> implements AdminDao
{

    private static final Logger LOG = LoggerFactory.getLogger(AdminDaoImpl.class.getName());

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
    public Admin findById(Integer id)
    {
        Admin entity = get(id);
        return entity;
    }

    @Override
    public Admin save(Admin bean)
    {
        getSession().save(bean);
        return bean;
    }

    @Override
    public Admin deleteById(Integer id)
    {
        Admin entity = super.get(id);
        if (entity != null)
        {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<Admin> getEntityClass()
    {
        return Admin.class;
    }

}
