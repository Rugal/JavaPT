package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.entity.User;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface UserService
{

    User deleteById(Integer id);

    @Transactional(readOnly = true)
    User getByID(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    User save(User bean);

    User update(User bean);

}
