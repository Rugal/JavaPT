package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.common.AliasToBeanNestedResultTransformer;
import ga.rugal.jpt.core.dao.ThreadDao;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.Thread;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import ml.rugal.sshcommon.page.Pagination;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Repository
public class ThreadDaoImpl extends HibernateBaseDao<Thread, Integer> implements ThreadDao
{

    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(Post post, int pageNo, int pageSize)
    {
        Criteria criteria = createCriteria();
        criteria.createAlias("replyer", "r");
        criteria.setProjection(Projections.projectionList()
            .add(Projections.id().as("tid"))
            .add(Projections.property("content").as("content"))
            .add(Projections.property("createTime").as("createTime"))
            .add(Projections.property("judgement").as("judgement"))
            .add(Projections.property("createTime").as("createTime"))
            .add(Projections.property("r.uid").as("replyer.uid"))
            .add(Projections.property("r.username").as("replyer.username"))
        );
        criteria.setResultTransformer(new AliasToBeanNestedResultTransformer(Thread.class));
        return findByCriteria(criteria, pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Thread> getByPID(Post post)
    {
        return super.findByProperty("post", post);
    }

    @Override
    protected Class<Thread> getEntityClass()
    {
        return Thread.class;
    }
}
