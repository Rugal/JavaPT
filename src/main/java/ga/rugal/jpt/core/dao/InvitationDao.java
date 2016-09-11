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

    Invitation updateByUpdater(Updater<Invitation> updater);

    Invitation delete(Invitation bean);

    Invitation save(Invitation bean);

    Pagination getPage(int pageNo, int pageSize);

    Invitation get(String id);

    /**
     * Just creates the invitation code with BCrypt algorithm, length is 15.
     *
     * @return
     */
    String generate();
}
