package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.entity.UserLevel;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface LevelService
{

    UserLevel deleteById(Integer id);

    @Transactional(readOnly = true)
    UserLevel getByID(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    UserLevel save(UserLevel bean);

    UserLevel update(UserLevel bean);

}
