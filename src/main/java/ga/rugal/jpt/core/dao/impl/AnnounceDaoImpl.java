package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.AnnounceDao;
import ga.rugal.jpt.core.entity.Announce;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import java.util.List;
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
public class AnnounceDaoImpl extends HibernateBaseDao<Announce, Long> implements AnnounceDao
{

    @Override
    protected Class<Announce> getEntityClass()
    {
        return Announce.class;
    }

    @Override
    @Transactional(readOnly = true)
    public Announce findLastAnnounce(User user, Post post)
    {
        Criteria crit = createCriteria();
        if (null != user)
        {
            crit.add(Restrictions.eq("user", user));
        }
        if (null != post)
        {
            crit.add(Restrictions.eq("post", post));
        }
        crit.addOrder(Order.desc("announceTime"));
        crit.setMaxResults(1);
        List<Announce> list = crit.list();
        return list.isEmpty() ? null : list.get(0);
    }
}
