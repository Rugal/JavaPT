package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.PostTag;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface PostTagsDao
{

    PostTag delete(PostTag bean);

    PostTag get(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    PostTag save(PostTag bean);

    PostTag updateByUpdater(Updater<PostTag> updater);
}
