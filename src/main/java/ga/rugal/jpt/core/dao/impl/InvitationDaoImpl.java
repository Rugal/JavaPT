package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.InvitationDao;
import ga.rugal.jpt.core.entity.Invitation;
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
public class InvitationDaoImpl extends HibernateBaseDao<Invitation, Integer> implements InvitationDao
{

    private static final Logger LOG = LoggerFactory.getLogger(InvitationDaoImpl.class.getName());

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
    public Invitation findById(Integer id)
    {
        Invitation entity = get(id);
        return entity;
    }

    @Override
    public Invitation save(Invitation bean)
    {
        getSession().save(bean);
        return bean;
    }

    @Override
    public Invitation deleteById(Integer id)
    {
        Invitation entity = super.get(id);
        if (entity != null)
        {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<Invitation> getEntityClass()
    {
        return Invitation.class;
    }

}
