package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Level;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;

/**
 *
 * @author Rugal Bernstein
 */
public interface LevelDao
{

    Level delete(Level bean);

    Level get(Integer id);

    Pagination getPage(int pageNo, int pageSize);

    Level save(Level bean);

    Level getLevel(Long upload, Long download);

    Level updateByUpdater(Updater<Level> updater);
}
