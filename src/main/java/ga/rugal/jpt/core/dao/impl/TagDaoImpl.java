package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.TagDao;
import ga.rugal.jpt.core.entity.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
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

    @Transactional(readOnly = true)
    @Override
    public List<Tag> findByName(String partialName)
    {
        List<Tag> list = super.contains("name", partialName);
        return list;
    }

    @Override
    protected Class<Tag> getEntityClass()
    {
        return Tag.class;
    }

}
