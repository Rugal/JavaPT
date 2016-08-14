package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.dao.LevelDao;

/**
 *
 * @author Rugal Bernstein
 */
public interface LevelService extends BaseService<LevelDao>
{

    Level update(Level bean);
}
