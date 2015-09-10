package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.User;
import java.util.List;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface AdminService
{

    Admin deleteById(Integer id);

    @Transactional(readOnly = true)
    Admin getByID(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    Admin save(Admin bean);

    Admin update(Admin bean);

    @Transactional(readOnly = true)
    List<Admin> getByUID(User uid);

}
