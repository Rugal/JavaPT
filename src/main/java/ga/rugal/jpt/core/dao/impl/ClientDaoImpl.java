package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.core.dao.ClientDao;
import ga.rugal.jpt.core.entity.Client;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.Finder;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Repository
public class ClientDaoImpl extends HibernateBaseDao<Client, Integer> implements ClientDao
{

    @Override
    @Transactional(readOnly = true)
    public Client getByPeerID(String cname, String version)
    {
        String hql = "FROM Client bean WHERE bean.cname=:cname AND bean.version=:version";
        Finder f = Finder.create(hql);
        f.setParam("cname", cname);
        f.setParam("version", version);
        f.setMaxResults(1);
        List<Client> list = find(f);
        if (list.isEmpty())
        {
            return null;
        }
        return list.get(0);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public Client findByPeerID(String cname, String version)
    {
        Client client = this.getByPeerID(cname, version);
        if (null == client)
        {
            LOG.debug(CommonLogContent.CLIENT_VERSION_NOT_FOUND, cname, version);
            client = this.getByPeerID(cname, "*");
            if (null == client)
            {
                LOG.debug(CommonLogContent.CLIENT_NOT_FOUND, cname);
                client = this.get(0);
                //this method will find the default client for others.
                //maybe use getByPeerID("*", "*") will be better.
                LOG.debug(CommonLogContent.OTHER_CLIENT, client.getEnable() ? "enabled" : "disabled");
            }
        }
        return client;
    }

    @Override
    protected Class<Client> getEntityClass()
    {
        return Client.class;
    }
}
