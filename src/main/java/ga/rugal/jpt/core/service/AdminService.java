package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.dao.AdminDao;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.User;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface AdminService extends BaseService<AdminDao>
{

    Admin update(Admin bean);

    /**
     * Tell if the user is administrator.
     *
     * @param user
     *
     * @return
     */
    @Transactional(readOnly = true)
    boolean isAdmin(User user);

    /**
     * Tell if a user is administrator with specific <code>admin.level</code>
     *
     * @param user
     * @param levels
     *
     * @return
     */
    @Transactional(readOnly = true)
    boolean meetAdminLevels(User user, Admin.Role... levels);
}
