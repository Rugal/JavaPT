package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.entity.Thread;
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
    Thread findById(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    Thread save(Thread bean);

    Thread update(Thread bean);

}
