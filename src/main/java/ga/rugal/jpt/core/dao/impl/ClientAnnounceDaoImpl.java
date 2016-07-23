package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.ClientAnnounceDao;
import ga.rugal.jpt.core.entity.ClientAnnounce;
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
public class ClientAnnounceDaoImpl extends HibernateBaseDao<ClientAnnounce, Long> implements ClientAnnounceDao
{

    @Override
    protected Class<ClientAnnounce> getEntityClass()
    {
        return ClientAnnounce.class;
    }

    @Override
    @Transactional(readOnly = true)
    public ClientAnnounce findLastAnnounce(User user, Post post)
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
        List<ClientAnnounce> list = crit.list();
        ClientAnnounce ca = null;
        if (!list.isEmpty())
        {
            ca = list.get(0);
        }
        return ca;
    }

}
