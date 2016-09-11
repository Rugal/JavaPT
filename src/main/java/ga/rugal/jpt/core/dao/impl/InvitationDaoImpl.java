package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.InvitationDao;
import ga.rugal.jpt.core.entity.Invitation;
import ga.rugal.jpt.core.entity.User;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Repository
public class InvitationDaoImpl extends HibernateBaseDao<Invitation, String> implements InvitationDao
{

    @Override
    protected Class<Invitation> getEntityClass()
    {
        return Invitation.class;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isUsable(User invitor, String id)
    {
        Criteria crit = createCriteria();
        crit.add(Restrictions.eq("id", id));
        crit.add(Restrictions.eq("invitor", invitor));
        crit.add(Restrictions.isNull("invitee"));
        return !crit.list().isEmpty();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<String> getUsableCode(User invitor)
    {
        Criteria crit = createCriteria();
        crit.add(Restrictions.eq("invitor", invitor));
        crit.add(Restrictions.isNull("invitee"));
        crit.setProjection(Projections.property("id"));
        return crit.list();
    }
}
