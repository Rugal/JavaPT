package ga.rugal.jpt.core.service;

import ga.rugal.jpt.common.tracker.common.Torrent;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.entity.Post;

/**
 *
 * @author Rugal Bernstein
 */
public interface PostService extends BaseService<PostDao>
{

    Post update(Post bean);

    Post save(Post bean, Torrent torrent);
}
