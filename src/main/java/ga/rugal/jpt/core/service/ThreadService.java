package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.Thread;
import java.util.List;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface ThreadService
{

    Thread deleteById(Integer id);

    @Transactional(readOnly = true)
    Thread getByID(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(Post post, int pageNo, int pageSize);

    Thread save(Thread bean);

    Thread update(Thread bean);

    @Transactional(readOnly = true)
    List<Thread> getByPID(Post post);

}
