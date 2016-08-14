package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import ml.rugal.sshcommon.page.Pagination;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Repository
public class UserDaoImpl extends HibernateBaseDao<User, Integer> implements UserDao
{

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Pagination findByName(String username, Integer pageNo, Integer pageSize)
    {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.like("username", "%" + username + "%"));
        return findByCriteria(criteria, pageNo, pageSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public User getByEmail(String email)
    {
        return super.findUniqueByProperty("email", email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean authenticate(Integer uid, String password)
    {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("uid", uid));
        criteria.add(Restrictions.eq("password", password));
        criteria.setProjection(Projections.count("uid"));
        return ((Number) criteria.uniqueResult()).intValue() == 1;
//        String hql = "SELECT count(1) FROM User bean WHERE bean.uid=:uid AND bean.password=:password";
//        Query query = getSession().createQuery(hql);
//        query.setParameter("uid", uid);
//        query.setParameter("password", password);
//        return ((Number) query.iterate().next()).intValue() == 1;
    }

    @Override
    protected Class<User> getEntityClass()
    {
        return User.class;
    }
}
