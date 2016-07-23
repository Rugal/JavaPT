package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.SigninLog;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;

/**
 *
 * @author Rugal Bernstein
 */
public interface SigninLogDao
{

    SigninLog delete(SigninLog bean);

    SigninLog get(Integer id);

    Pagination getPage(int pageNo, int pageSize);

    SigninLog save(SigninLog bean);

    SigninLog updateByUpdater(Updater<SigninLog> updater);

}
