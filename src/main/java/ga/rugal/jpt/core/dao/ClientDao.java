package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Client;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface ClientDao
{

    Client delete(Client bean);

    Client get(Integer id);

    Pagination getPage(int pageNo, int pageSize);

    Client save(Client bean);

    Client updateByUpdater(Updater<Client> updater);

    /**
     * Get an exact client by extracted cname and version in detail.
     * <p>
     * Different from {@link #findByPeerID(java.lang.String, java.lang.String) }, this method will
     * not try to find parent client if unable to find exact one.
     * <p>
     * @param cname
     * @param version <p>
     * @return null if unable to find the exact client software.
     */
    @Transactional(readOnly = true)
    Client getByPeerID(String cname, String version);

    /**
     * This method try to find a suitable client for requested cname and version, that is:
     * <p>
     * if unable to find identical client, it will try to search through the parent one by the
     * requested cname.
     * <p>
     * If still unable to find, this method will get the default client to see the global setting.
     * <p>
     * @param cname
     * @param version <p>
     * @return This method will always return a client, either exact one if could find, or the
     *         parent level one.
     */
    @Transactional(readOnly = true)
    Client findByPeerID(String cname, String version);
}
