package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.PostTags;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;

/**
 *
 * @author Rugal Bernstein
 */
public interface PostTagsDao
{

    PostTags deleteById(Integer id);

    PostTags getByID(Integer id);

    Pagination getPage(int pageNo, int pageSize);

    PostTags save(PostTags bean);

    PostTags updateByUpdater(Updater<PostTags> updater);
}
