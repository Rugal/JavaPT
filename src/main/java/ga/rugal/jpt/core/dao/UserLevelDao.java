package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.UserLevel;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;

/**
 *
 * @author Rugal Bernstein
 */
public interface UserLevelDao
{

    UserLevel delete(UserLevel bean);

    UserLevel get(Integer id);

    Pagination getPage(int pageNo, int pageSize);

    UserLevel save(UserLevel bean);

    UserLevel getLevel(Integer credit);

    UserLevel updateByUpdater(Updater<UserLevel> updater);
}
