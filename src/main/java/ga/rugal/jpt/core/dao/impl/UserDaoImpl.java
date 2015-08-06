package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.User;
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
public class UserDaoImpl extends HibernateBaseDao<User, Integer> implements UserDao
{

    private static final Logger LOG = LoggerFactory.getLogger(User.class.getName());

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
    public User findById(Integer id)
    {
        User entity = get(id);
        return entity;
    }

    @Override
    public User save(User bean)
    {
        getSession().save(bean);
        return bean;
    }

    @Override
    public User deleteById(Integer id)
    {
        User entity = super.get(id);
        if (entity != null)
        {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<User> getEntityClass()
    {
        return User.class;
    }

}
