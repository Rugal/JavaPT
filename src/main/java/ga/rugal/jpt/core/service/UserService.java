package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.ClientAnnounce;
import ga.rugal.jpt.core.entity.User;

/**
 *
 * @author Rugal Bernstein
 */
public interface UserService extends BaseService<UserDao>
{

    User update(User bean);

    User clientAnnounce(User bean, ClientAnnounce clientAnnounce);
}
