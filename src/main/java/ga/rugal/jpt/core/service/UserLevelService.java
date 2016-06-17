package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.dao.UserLevelDao;
import ga.rugal.jpt.core.entity.UserLevel;

/**
 *
 * @author Rugal Bernstein
 */
public interface UserLevelService extends BaseService<UserLevelDao>
{

    UserLevel update(UserLevel bean);
}
