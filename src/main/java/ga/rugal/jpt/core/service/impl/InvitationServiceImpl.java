package ga.rugal.jpt.core.service.impl;

import config.SystemDefaultProperties;
import ga.rugal.jpt.core.dao.InvitationDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Invitation;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.InvitationService;
import ml.rugal.sshcommon.hibernate.Updater;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Service
@Transactional
public class InvitationServiceImpl implements InvitationService
{

    @Autowired
    private InvitationDao dao;

    @Autowired
    private UserDao userDao;

    @Override
    public InvitationDao getDAO()
    {
        return dao;
    }

    /**
     * Just creates the invitation code with BCrypt algorithm, length is 15.<BR>
     * Make sure the code is not duplicate.
     *
     * @return
     */
    private String generateCode()
    {
        String code;
        do
        {
            code = BCrypt.gensalt().substring(0, SystemDefaultProperties.INVITATION_CODE_LENGTH);

        } while (null != dao.get(code));
        return code;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Invitation generate(User invitor)
    {
        //create invitation object
        Invitation inv = new Invitation();
        inv.setId(this.generateCode());
        inv.setInvitor(invitor);
        //update user credit
        invitor.updateCredit(SystemDefaultProperties.INVITATION_CREDIT_NEED * -1);//negative
        Updater<User> updater = new Updater<>(invitor);
        updater.setUpdateMode(Updater.UpdateMode.MIN);
        updater.include("credit");
        userDao.updateByUpdater(updater);
        //
        return dao.save(inv);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Invitation consume(User invitee, Invitation invitation)
    {
        invitation.setInvitee(invitee);
        //
        Updater<Invitation> updater = new Updater<>(invitation);
        updater.setUpdateMode(Updater.UpdateMode.MIN);
        updater.include("invitee");
        return dao.updateByUpdater(updater);
    }

    @Override
    public Invitation update(Invitation bean)
    {
        Updater<Invitation> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }
}
