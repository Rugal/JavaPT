package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.common.AliasToBeanNestedResultTransformer;
import ga.rugal.jpt.core.dao.TaggingDao;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.Tag;
import ga.rugal.jpt.core.entity.Tagging;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Repository
public class TaggingDaoImpl extends HibernateBaseDao<Tagging, Integer> implements TaggingDao
{

    @Override
    protected Class<Tagging> getEntityClass()
    {
        return Tagging.class;
    }

    @Override
    @Transactional(readOnly = true)
    public Tagging get(Post post, Tag tag)
    {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("tag", tag));
        criteria.add(Restrictions.eq("post", post));
        return (Tagging) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> getTags(Post post)
    {
        Criteria criteria = this.getSession().createCriteria(this.getEntityClass());
        criteria.add(Restrictions.eq("post", post));
        criteria.createAlias("tag", "t");
        criteria.setProjection(Projections.projectionList()
            .add(Projections.property("t.tid").as("tid"))
            .add(Projections.property("t.name").as("name"))
        );
        criteria.setResultTransformer(new AliasToBeanNestedResultTransformer(Tag.class));
        return (List<Tag>) criteria.list();
    }

}
