package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.entity.ClientAnnounce;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface ClientAnnounceService
{

    ClientAnnounce deleteById(Long id);

    @Transactional(readOnly = true)
    ClientAnnounce getByID(Long id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    ClientAnnounce save(ClientAnnounce bean);

    ClientAnnounce update(ClientAnnounce bean);

}
