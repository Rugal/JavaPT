package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Invitation;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;

/**
 *
 * @author Rugal Bernstein
 */
public interface InvitationDao
{

    Invitation delete(Invitation bean);

    Invitation get(Integer id);

    Pagination getPage(int pageNo, int pageSize);

    Invitation save(Invitation bean);

    Invitation updateByUpdater(Updater<Invitation> updater);

}
