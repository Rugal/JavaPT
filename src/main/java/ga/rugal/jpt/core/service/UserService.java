package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.entity.ClientAnnounce;
import ga.rugal.jpt.core.entity.User;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface UserService
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

    @Transactional(readOnly = true)
    boolean authenticateUser(Integer uid, String password);

    User save(User bean);

    User update(User bean);

    User clientAnnounce(User bean, ClientAnnounce clientAnnounce);

}
