package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Status;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;

/**
 *
 * @author Rugal Bernstein
 */
public interface StatusDao
{

    Status deleteById(Integer id);

    Status findById(Integer id);

    Pagination getPage(int pageNo, int pageSize);

    Status save(Status bean);

    Status updateByUpdater(Updater<Status> updater);

}
