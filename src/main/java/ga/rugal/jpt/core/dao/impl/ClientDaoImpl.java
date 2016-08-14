package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.core.dao.ClientDao;
import ga.rugal.jpt.core.entity.Client;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
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

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public Client getByPeerID(String cname, String version)
    {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("cname", cname));
        criteria.add(Restrictions.eq("version", version));
        criteria.setMaxResults(1);
        return (Client) criteria.uniqueResult();
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
                client = this.getByPeerID("*", "*");
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
