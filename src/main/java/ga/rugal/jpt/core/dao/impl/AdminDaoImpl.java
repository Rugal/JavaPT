package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.AdminDao;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.User;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Repository
public class AdminDaoImpl extends HibernateBaseDao<Admin, Integer> implements AdminDao
{

    @Override
    @Transactional(readOnly = true)
    public List<Admin> getByUID(User user)
    {
        Criteria crit = createCriteria();
        crit.add(Restrictions.eq("user", user));
        List<Admin> list = (List<Admin>) crit.list();
        return list;
    }

    @Override
    protected Class<Admin> getEntityClass()
    {
        return Admin.class;
    }
}
