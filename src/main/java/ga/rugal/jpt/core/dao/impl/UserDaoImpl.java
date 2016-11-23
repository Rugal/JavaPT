package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.common.AliasToBeanNestedResultTransformer;
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
        criteria.setProjection(Projections.projectionList()
            .add(Projections.id().as("uid"))
            .add(Projections.property("username").as("username"))
            .add(Projections.property("upload").as("upload"))
            .add(Projections.property("download").as("download"))
            .add(Projections.property("credit").as("credit"))
            .add(Projections.property("status").as("status"))
        );
        criteria.setResultTransformer(new AliasToBeanNestedResultTransformer(User.class));
        return findByCriteria(criteria, pageNo, pageSize);
    }

    private User removeKeyInfo(User bean)
    {
        if (null != bean)
        {
            bean.setPassword(null);
        }
        return bean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public User get(Integer id)
    {
        User bean = super.get(id);
        return removeKeyInfo(bean);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public User getByEmail(String email)
    {
        User bean = super.findUniqueByProperty("email", email);
        return removeKeyInfo(bean);
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
    }

    @Override
    protected Class<User> getEntityClass()
    {
        return User.class;
    }
}
