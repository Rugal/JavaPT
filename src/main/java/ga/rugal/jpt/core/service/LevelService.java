package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.entity.Level;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface LevelService
{

    Level deleteById(Integer id);

    @Transactional(readOnly = true)
    Level getByID(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    Level save(Level bean);

    Level update(Level bean);

}
