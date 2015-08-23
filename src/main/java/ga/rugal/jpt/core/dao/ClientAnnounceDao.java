package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.ClientAnnounce;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface ClientAnnounceDao
{

    ClientAnnounce deleteById(Long id);

    @Transactional(readOnly = true)
    ClientAnnounce getByID(Long id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    ClientAnnounce save(ClientAnnounce bean);

    ClientAnnounce updateByUpdater(Updater<ClientAnnounce> updater);

}
