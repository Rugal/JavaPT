package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.Thread;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface ThreadDao
{

    Thread deleteById(Integer id);

    @Transactional(readOnly = true)
    Thread getByID(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(Post post, int pageNo, int pageSize);

    Thread save(Thread bean);

    Thread updateByUpdater(Updater<Thread> updater);

}
