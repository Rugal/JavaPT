package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.InvitationDao;
import ga.rugal.jpt.core.entity.Invitation;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import org.mindrot.jbcrypt.BCrypt;
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

    @Override
    public String generate()
    {
        return BCrypt.gensalt().substring(0, 15);
    }
}
