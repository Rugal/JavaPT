package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.dao.PostTagsDao;
import ga.rugal.jpt.core.entity.PostTag;

/**
 *
 * @author Rugal Bernstein
 */
public interface PostTagsService extends BaseService<PostTagsDao>
{

    PostTag update(PostTag bean);
}
