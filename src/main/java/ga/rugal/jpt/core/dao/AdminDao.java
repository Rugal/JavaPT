package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.User;
import java.util.List;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface AdminDao
{

    Admin updateByUpdater(Updater<Admin> updater);

    /**
     * Get the list of admin roles of a user.
     *
     * @param user The user bean.
       *
     * @return
     */
    @Transactional(readOnly = true)
    List<Admin> getByUID(User user);

    Admin delete(Admin bean);

    Admin save(Admin bean);

    Pagination getPage(int pageNo, int pageSize);

    Admin get(Integer id);
}
