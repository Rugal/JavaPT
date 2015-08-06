package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.SigninLogDao;
import ga.rugal.jpt.core.entity.SigninLog;
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
public class SigninLogDaoImpl extends HibernateBaseDao<SigninLog, Integer> implements SigninLogDao
{

    private static final Logger LOG = LoggerFactory.getLogger(SigninLog.class.getName());

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
    public SigninLog findById(Integer id)
    {
        SigninLog entity = get(id);
        return entity;
    }

    @Override
    public SigninLog save(SigninLog bean)
    {
        getSession().save(bean);
        return bean;
    }

    @Override
    public SigninLog deleteById(Integer id)
    {
        SigninLog entity = super.get(id);
        if (entity != null)
        {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<SigninLog> getEntityClass()
    {
        return SigninLog.class;
    }

}
