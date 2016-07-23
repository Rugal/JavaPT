package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.dao.InvitationDao;
import ga.rugal.jpt.core.entity.Invitation;
import ga.rugal.jpt.core.service.InvitationService;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Service
@Transactional
public class InvitationServiceImpl implements InvitationService
{

    @Autowired
    private InvitationDao dao;

    @Override
    public Invitation update(Invitation bean)
    {
        Updater<Invitation> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

    @Override
    public InvitationDao getDAO()
    {
        return this.dao;
    }
}
