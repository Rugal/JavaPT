package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.User;
import java.util.List;
import ml.rugal.sshcommon.hibernate.Finder;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import ml.rugal.sshcommon.page.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
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
public class UserDaoImpl extends HibernateBaseDao<User, Integer> implements UserDao
{

    private static final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class.getName());

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
    public User getByID(Integer id)
    {
        User entity = get(id);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isEmailAvailable(String email)
    {
        return super.countByProperty("email", email) == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isUserNameAvailable(String username)
    {
        return super.countByProperty("username", username) == 0;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean authenticateUser(Integer uid, String password)
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

    public List<Object> getList(String ip, Integer userId)
    {
        String hql = " from BbsLimit bean where 1=1 ";
        Finder f = Finder.create(hql);
        if (StringUtils.isNotBlank(ip))
        {
            f.append(" and bean.ip=:ip").setParam("ip", ip);
        }
        if (userId != null)
        {
            f.append(" and bean.userId=:userId").setParam("userId", userId);
        }
        return find(f);
    }

    public List<Object> getListByUserIdStatus(Integer userId, Integer typeId,
                                              Boolean status)
    {
        String hql = "from BbsMessage bean where  bean.user.id=:userId and bean.msgType=:typeId and bean.status=:status ";
        Finder f = Finder.create(hql);
        f.setParam("userId", userId);
        f.setParam("typeId", typeId);
        f.setParam("status", status);
        return find(f);
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
