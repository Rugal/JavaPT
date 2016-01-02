package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.ThreadDao;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.Thread;
import java.util.List;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import ml.rugal.sshcommon.page.Pagination;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Repository
public class ThreadDaoImpl extends HibernateBaseDao<Thread, Integer> implements ThreadDao
{

    private static final Logger LOG = LoggerFactory.getLogger(ThreadDaoImpl.class.getName());

    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(Post post, int pageNo, int pageSize)
    {
        Criteria crit = createCriteria();
        crit.add(Restrictions.eq("post", post));
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Thread> getByPID(Post post)
    {
        return super.findByProperty("post", post);
    }

    @Override
    @Transactional(readOnly = true)
    public Thread getByID(Integer id)
    {
        Thread entity = get(id);
        return entity;
    }

    @Override
    public Thread save(Thread bean)
    {
        bean.setPostTime(System.currentTimeMillis());
        getSession().save(bean);
        return bean;
    }

    @Override
    public Thread deleteById(Integer id)
    {
        Thread entity = super.get(id);
        if (entity != null)
        {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<Thread> getEntityClass()
    {
        return Thread.class;
    }

}
