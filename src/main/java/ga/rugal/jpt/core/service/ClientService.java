package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.dao.ClientDao;
import ga.rugal.jpt.core.entity.Client;

/**
 *
 * @author Rugal Bernstein
 */
public interface ClientService extends BaseService<ClientDao>
{

    Client update(Client bean);
}
