package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.dao.ClientDao;
import ga.rugal.jpt.core.entity.Client;
import ga.rugal.jpt.core.service.ClientService;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService
{

    private static final Logger LOG = LoggerFactory.getLogger(ClientServiceImpl.class.getName());

    @Autowired
    private ClientDao dao;

    @Override
    @Transactional(readOnly = true)
    public Client findByPeerID(String peerIDText)
    {
        //-AZ2060-
        String peerID = peerIDText.split("-")[1];
        String client = peerID.substring(0, 2);
        String version = peerID.substring(2);
        return dao.getByPeerID(client, version);
    }

    @Override
    @Transactional(readOnly = true)
    public Client getByPeerID(String cname, String version)
    {
        return dao.getByPeerID(cname, version);
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize)
    {
        return dao.getPage(pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public Client getByID(Integer id)
    {
        return dao.getByID(id);
    }

    @Override
    public Client save(Client bean)
    {
        return dao.save(bean);
    }

    @Override
    public Client deleteById(Integer id)
    {
        return dao.deleteById(id);
    }

    @Override
    public Client update(Client bean)
    {
        Updater<Client> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

}
