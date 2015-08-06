package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Thread;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;

/**
 *
 * @author Rugal Bernstein
 */
public interface ThreadDao
{

    Thread deleteById(Integer id);

    Thread findById(Integer id);

    Pagination getPage(int pageNo, int pageSize);

    Thread save(Thread bean);

    Thread updateByUpdater(Updater<Thread> updater);

}
