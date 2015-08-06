package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.service.InvitationService;
import ga.rugal.jpt.core.dao.InvitationDao;
import ga.rugal.jpt.core.entity.Invitation;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(InvitationServiceImpl.class.getName());

    @Autowired
    private InvitationDao dao;

    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize)
    {
        return dao.getPage(pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public Invitation findById(Integer id)
    {
        return dao.findById(id);
    }

    @Override
    public Invitation save(Invitation bean)
    {
        return dao.save(bean);
    }

    @Override
    public Invitation deleteById(Integer id)
    {
        return dao.deleteById(id);
    }

    @Override
    public Invitation update(Invitation bean)
    {
        Updater<Invitation> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

}
