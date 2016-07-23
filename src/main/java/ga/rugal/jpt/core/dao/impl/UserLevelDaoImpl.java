package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.UserLevelDao;
import ga.rugal.jpt.core.entity.UserLevel;
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
public class UserLevelDaoImpl extends HibernateBaseDao<UserLevel, Integer> implements UserLevelDao
{

    @Override
    @Transactional(readOnly = true)
    public UserLevel getLevel(Integer credit)
    {
        //select * from level where minimum < 7000 order by minimum desc limit 1;
        Criteria crit = createCriteria();
        crit.add(Restrictions.lt("minimum", credit));
        crit.addOrder(Order.desc("minimum"));
        crit.setMaxResults(1);
        return (UserLevel) crit.list().get(0);
    }

    @Override
    protected Class<UserLevel> getEntityClass()
    {
        return UserLevel.class;
    }
}
