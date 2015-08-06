package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Client;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;

/**
 *
 * @author Rugal Bernstein
 */
public interface ClientDao
{

    Client deleteById(Integer id);

    Client getByID(Integer id);

    Pagination getPage(int pageNo, int pageSize);

    Client save(Client bean);

    Client updateByUpdater(Updater<Client> updater);
}
