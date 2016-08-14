package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.entity.PostTag;
import ga.rugal.jpt.core.dao.TaggingDao;

/**
 *
 * @author Rugal Bernstein
 */
public interface TaggingService extends BaseService<TaggingDao>
{

    PostTag update(PostTag bean);
}
