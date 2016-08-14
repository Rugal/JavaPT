package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.TagDao;
import ga.rugal.jpt.core.entity.Tag;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import ml.rugal.sshcommon.page.Pagination;
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
public class TagDaoImpl extends HibernateBaseDao<Tag, Integer> implements TagDao
{

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public Pagination findByName(String partialName, int pageNo, int pageSize)
    {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.like("name", "%" + partialName + "%"));
        return findByCriteria(criteria, pageNo, pageSize);
    }

    @Override
    protected Class<Tag> getEntityClass()
    {
        return Tag.class;
    }

}
