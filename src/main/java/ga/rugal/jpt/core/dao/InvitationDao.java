package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Invitation;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface InvitationDao
{

    Invitation deleteById(Integer id);

    @Transactional(readOnly = true)
    Invitation findById(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    Invitation save(Invitation bean);

    Invitation updateByUpdater(Updater<Invitation> updater);

}
