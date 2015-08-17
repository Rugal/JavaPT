package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.PostTags;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface PostTagsDao
{

    PostTags deleteById(Integer id);

    @Transactional(readOnly = true)
    PostTags getByID(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    PostTags save(PostTags bean);

    PostTags updateByUpdater(Updater<PostTags> updater);
}
