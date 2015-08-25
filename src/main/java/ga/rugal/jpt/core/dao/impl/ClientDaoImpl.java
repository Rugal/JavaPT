package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.ClientDao;
import ga.rugal.jpt.core.entity.Client;
import java.util.List;
import ml.rugal.sshcommon.hibernate.Finder;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import ml.rugal.sshcommon.page.Pagination;
import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Repository
public class ClientDaoImpl extends HibernateBaseDao<Client, Integer> implements ClientDao
{

    private static final Logger LOG = LoggerFactory.getLogger(ClientDaoImpl.class.getName());

    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize)
    {
        Criteria crit = createCriteria();
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public Client getByID(Integer id)
    {
        Client entity = get(id);
        return entity;
    }

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

//    @Transactional(readOnly = true)
//    public Client getExactByPeerID(String cname, String version)
//    {
//
//    }
    @Override
    public Client save(Client bean)
    {
        getSession().save(bean);
        return bean;
    }

    @Override
    public Client deleteById(Integer id)
    {
        Client entity = super.get(id);
        if (entity != null)
        {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<Client> getEntityClass()
    {
        return Client.class;
    }

}
