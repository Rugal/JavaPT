package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.entity.Level;
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
public class LevelDaoImpl extends HibernateBaseDao<Level, Integer> implements LevelDao
{

    private static final Logger LOG = LoggerFactory.getLogger(Level.class.getName());

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
    public Level findById(Integer id)
    {
        Level entity = get(id);
        return entity;
    }

    @Override
    public Level save(Level bean)
    {
        getSession().save(bean);
        return bean;
    }

    @Override
    public Level deleteById(Integer id)
    {
        Level entity = super.get(id);
        if (entity != null)
        {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<Level> getEntityClass()
    {
        return Level.class;
    }

}
