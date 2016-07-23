package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.dao.ClientDao;
import ga.rugal.jpt.core.entity.Client;
import ga.rugal.jpt.core.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Service
@Transactional
public class ClientServiceImpl implements ClientService
{

    @Autowired
    private ClientDao dao;

    @Override
    public Client update(Client bean)
    {
        Updater<Client> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

    @Override
    public ClientDao getDAO()
    {
        return this.dao;
    }
}
