package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.AdminLevel;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;

/**
 *
 * @author Rugal Bernstein
 */
public interface AdminLevelDao
{

    AdminLevel deleteById(Integer id);

    AdminLevel findById(Integer id);

    Pagination getPage(int pageNo, int pageSize);

    AdminLevel save(AdminLevel bean);

    AdminLevel updateByUpdater(Updater<AdminLevel> updater);
}
