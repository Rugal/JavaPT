package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.ClientAnnounceDao;
import ga.rugal.jpt.core.entity.ClientAnnounce;
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
public class ClientAnnounceDaoImpl extends HibernateBaseDao<ClientAnnounce, Long> implements ClientAnnounceDao
{

    private static final Logger LOG = LoggerFactory.getLogger(ClientAnnounceDaoImpl.class.getName());

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
    public ClientAnnounce getByID(Long id)
    {
        ClientAnnounce entity = get(id);
        return entity;
    }

    @Override
    public ClientAnnounce save(ClientAnnounce bean)
    {
        getSession().save(bean);
        return bean;
    }

    @Override
    public ClientAnnounce deleteById(Long id)
    {
        ClientAnnounce entity = super.get(id);
        if (entity != null)
        {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<ClientAnnounce> getEntityClass()
    {
        return ClientAnnounce.class;
    }

}
