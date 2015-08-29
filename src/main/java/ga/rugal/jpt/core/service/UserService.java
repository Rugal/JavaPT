package ga.rugal.jpt.core.service;

import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.core.entity.User;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface UserService
{

    User deleteById(Integer id);

    @Transactional(readOnly = true)
    User getByID(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    @Transactional(readOnly = true)
    boolean authenticateUser(Integer uid, String password);

    User save(User bean);

    User update(User bean);

    /**
     * Announce a update for user.
     * <p>
     * First log this announce.
     * <p>
     * Secondly to query get the difference between this update and the most recent one.
     * <p>
     * Then to update information in user profile.
     * <p>
     * @param bean
     *             <p>
     * @return
     */
    User announce(TrackerUpdateBean bean);

}
