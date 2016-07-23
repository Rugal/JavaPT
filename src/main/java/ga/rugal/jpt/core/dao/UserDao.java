package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.User;
import java.util.List;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface UserDao
{

    User delete(User bean);

    User get(Integer id);

    Pagination getPage(int pageNo, int pageSize);

    User save(User bean);

    User updateByUpdater(Updater<User> updater);

    /**
     * Verify user credential.
     *
     * @param uid
     * @param password
     *
     * @return
     */
    boolean authenticate(Integer uid, String password);

    /**
     * Get user with related email address.
     *
     * @param email
     *
     * @return
     */
    @Transactional(readOnly = true)
    User getByEmail(String email);

    /**
     * Find user by part of name.
     *
     * @param username
     *
     * @return
     */
    @Transactional(readOnly = true)
    List<User> findByName(String username);
}
