package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Admin;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface AdminDao
{

    Admin deleteById(Integer id);

    @Transactional(readOnly = true)
    Admin getByID(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    Admin save(Admin bean);

    Admin updateByUpdater(Updater<Admin> updater);
}
