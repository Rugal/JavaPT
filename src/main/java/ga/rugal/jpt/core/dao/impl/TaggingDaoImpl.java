package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.entity.PostTag;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import org.springframework.stereotype.Repository;
import ga.rugal.jpt.core.dao.TaggingDao;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Repository
public class TaggingDaoImpl extends HibernateBaseDao<PostTag, Integer> implements TaggingDao
{

    @Override
    protected Class<PostTag> getEntityClass()
    {
        return PostTag.class;
    }
}
