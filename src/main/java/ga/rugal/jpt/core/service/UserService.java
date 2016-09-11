package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Announce;
import ga.rugal.jpt.core.entity.User;

/**
 *
 * @author Rugal Bernstein
 */
public interface UserService extends BaseService<UserDao>
{

    User update(User bean);

    /**
     * Announce user usage.<BR>
     * Update user upload, downloading, left etc.,
     *
     * @param bean
     * @param announce
     *
     * @return
     */
    User announce(User bean, Announce announce);
}
