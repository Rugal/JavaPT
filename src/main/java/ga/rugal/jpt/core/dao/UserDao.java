package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.User;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface UserDao
{

    /**
     * See if given email address is usable in DB, i.e. it can used for registration.
     *
     * @param email
     *
     * @return true if it does available.
     */
    @Transactional(readOnly = true)
    boolean isEmailAvailable(String email);

    /**
     * See if given username is usable in DB, i.e. there is no confliction.
     *
     * @param username
     *
     * @return true if it does available.
     */
    @Transactional(readOnly = true)
    boolean isUserNameAvailable(String username);

    User deleteById(Integer id);

    @Transactional(readOnly = true)
    User getByID(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    User save(User bean);

    User updateByUpdater(Updater<User> updater);

    boolean authenticateUser(Integer uid, String password);
}
