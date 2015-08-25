package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.entity.Client;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface ClientService
{

    Client deleteById(Integer id);

    @Transactional(readOnly = true)
    Client getByID(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    Client save(Client bean);

    Client update(Client bean);

    Client findByPeerID(String peerID);

    @Transactional(readOnly = true)
    Client getByPeerID(String cname, String version);

    @Transactional(readOnly = true)
    Client findByPeerID(String cname, String version);

}
