package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.dao.TagDao;
import ga.rugal.jpt.core.entity.Tag;

/**
 *
 * @author Rugal Bernstein
 */
public interface TagService extends BaseService<TagDao>
{

    Tag update(Tag bean);
}
