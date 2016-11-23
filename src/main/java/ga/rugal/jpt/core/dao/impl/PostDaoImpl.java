package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.common.AliasToBeanNestedResultTransformer;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.entity.Post;
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
public class PostDaoImpl extends HibernateBaseDao<Post, Integer> implements PostDao
{

    /**
     * Get a page of objects.
     *
     * @param pageNo   The page number
     * @param pageSize Size of each page
     *
     * @return A page of target objects.
     */
    @Transactional(readOnly = true)
    @Override
    public Pagination getPage(int pageNo, int pageSize)
    {
        Criteria criteria = this.getSession().createCriteria(this.getEntityClass());
        criteria.createAlias("author", "a");
        criteria.setProjection(Projections.projectionList()
            .add(Projections.id().as("pid"))
            .add(Projections.property("title").as("title"))
            .add(Projections.property("level").as("level"))
            .add(Projections.property("rate").as("rate"))
            .add(Projections.property("createTime").as("createTime"))
            .add(Projections.property("a.uid").as("author.uid"))
            .add(Projections.property("a.username").as("author.username"))
        );
        criteria.setResultTransformer(new AliasToBeanNestedResultTransformer(Post.class));
        return findByCriteria(criteria, pageNo, pageSize);
    }

    private Post removeKeyInfo(Post bean)
    {
        if (null != bean)
        {
            bean.setBencode(null);
        }
        return bean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Post get(Integer pid)
    {
        return this.get(pid, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Post get(Integer pid, boolean withBencode)
    {
        Post bean = super.get(pid);
        return withBencode ? bean : this.removeKeyInfo(bean);
    }

    @Override
    @Transactional(readOnly = true)
    public Post getByInfohash(String infoHash)
    {
        Post bean = this.findUniqueByProperty("hash", infoHash);
        return this.removeKeyInfo(bean);
    }

    @Override
    @Transactional(readOnly = true)
    public List getAllTorrentsOnly()
    {
        Criteria crit = createCriteria();
        crit.setProjection(Projections.property("bencode"));
        return crit.list();
    }

    @Override
    protected Class<Post> getEntityClass()
    {
        return Post.class;
    }
}
