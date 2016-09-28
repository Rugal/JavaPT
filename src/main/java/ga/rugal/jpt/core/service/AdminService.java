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
    /**
     * Tell if the user is an {@link ga.rugal.jpt.core.entity.Admin}. User is considered as an admin if and only if it
     * has at least one {@code Admin.Role}.
     *
     * @param user
     *
     * @return
     */
    @Transactional(readOnly = true)
    boolean isAdmin(User user);

    /**
     * Tell if a user is admin with all the specified <code>Admin.Role</code>
     *
     * @param user  The user to be inspected
     * @param roles
     *
     * @return true only if user has all the specified roles; otherwise, false
     */
    @Transactional(readOnly = true)
    boolean meetAllAdminLevels(User user, Admin.Role... roles);

    /**
     * Test if the given user has any of the specific {@code Admin.Role}.
     *
     * @param user  The user to be inspected
     * @param roles
     *
     * @return true if user has at least one of the specified roles; otherwise, false
     */
    @Transactional(readOnly = true)
    boolean meetAnyAdminLevel(User user, Admin.Role... roles);
}
