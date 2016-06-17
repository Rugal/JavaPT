package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.dao.PostTagsDao;
import ga.rugal.jpt.core.entity.PostTags;

/**
 *
 * @author Rugal Bernstein
 */
public interface PostTagsService extends BaseService<PostTagsDao>
{

    PostTags update(PostTags bean);
}
