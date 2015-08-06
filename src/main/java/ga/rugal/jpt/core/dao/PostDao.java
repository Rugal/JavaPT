package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Post;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;

/**
 *
 * @author Rugal Bernstein
 */
public interface PostDao
{

    Post deleteById(Integer id);

    Post getByID(Integer id);

    Pagination getPage(int pageNo, int pageSize);

    Post save(Post bean);

    Post updateByUpdater(Updater<Post> updater);
}
