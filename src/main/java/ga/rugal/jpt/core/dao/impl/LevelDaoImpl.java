package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.entity.Level;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import ml.rugal.sshcommon.page.Pagination;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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

    private static final Logger LOG = LoggerFactory.getLogger(LevelDaoImpl.class.getName());

    @Override
    @Transactional(readOnly = true)
    public Level getLevel(Integer credit)
    {
        //select * from level where minimum < 7000 order by minimum desc limit 1;

        Criteria crit = createCriteria();
        crit.add(Restrictions.lt("minimum", credit));
        crit.addOrder(Order.desc("minimum"));
        crit.setMaxResults(1);
        return (Level) crit.list().get(0);
    }

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
    public Level getByID(Integer id)
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
