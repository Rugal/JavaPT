package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.entity.Level;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Repository
public class LevelDaoImpl extends HibernateBaseDao<Level, Integer> implements LevelDao
{

    @Override
    @Transactional(readOnly = true)
    public Level getLevel(Long upload, Long download)
    {
        Criteria crit = createCriteria();
        crit.add(Restrictions.le("download", download));
        crit.add(Restrictions.le("upload", upload));
        crit.addOrder(Order.desc("upload"));
        crit.setMaxResults(1);
        return (Level) crit.list().get(0);
    }

    @Override
    protected Class<Level> getEntityClass()
    {
        return Level.class;
    }
}
