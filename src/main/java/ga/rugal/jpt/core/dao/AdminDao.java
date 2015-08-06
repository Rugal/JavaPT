package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Admin;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;

/**
 *
 * @author Rugal Bernstein
 */
public interface AdminDao
{

    Admin deleteById(Integer id);

    Admin findById(Integer id);

    Pagination getPage(int pageNo, int pageSize);

    Admin save(Admin bean);

    Admin updateByUpdater(Updater<Admin> updater);
}
