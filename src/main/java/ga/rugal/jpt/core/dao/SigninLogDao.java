package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.SigninLog;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface SigninLogDao
{

    SigninLog deleteById(Integer id);

    @Transactional(readOnly = true)
    SigninLog getByID(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    SigninLog save(SigninLog bean);

    SigninLog updateByUpdater(Updater<SigninLog> updater);

}
