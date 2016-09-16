package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.Thread;
import java.util.List;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface ThreadDao
{

    Thread delete(Thread bean);

    Thread get(Integer id);

    Pagination getPage(Post post, int pageNo, int pageSize);

    Thread save(Thread bean);

    Thread updateByUpdater(Updater<Thread> updater);

    /**
     * Get a list of threads under a specific post.
     *
     * @param post
     *
     * @return
     */
    @Transactional(readOnly = true)
    List<Thread> getByPID(Post post);
}
