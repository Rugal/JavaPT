package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.User;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;

/**
 *
 * @author Rugal Bernstein
 */
public interface UserDao
{

    User deleteById(Integer id);

    User findById(Integer id);

    Pagination getPage(int pageNo, int pageSize);

    User save(User bean);

    User updateByUpdater(Updater<User> updater);
}
