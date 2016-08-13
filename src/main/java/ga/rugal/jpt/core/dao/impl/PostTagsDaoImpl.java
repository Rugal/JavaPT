package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.PostTagsDao;
import ga.rugal.jpt.core.entity.PostTag;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Repository
public class PostTagsDaoImpl extends HibernateBaseDao<PostTag, Integer> implements PostTagsDao
{

    @Override
    protected Class<PostTag> getEntityClass()
    {
        return PostTag.class;
    }
}
