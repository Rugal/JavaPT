package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.PostTag;
import ga.rugal.jpt.core.entity.Tag;
import java.util.List;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface TaggingDao
{

    PostTag delete(PostTag bean);

    PostTag get(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    PostTag save(PostTag bean);

    PostTag updateByUpdater(Updater<PostTag> updater);

    /**
     * Get a Tagging of a post by tag. A Post should not have more than 1 tag with same Tag ID.
     *
     * @param post
     * @param tag
     *
     * @return
     */
    @Transactional(readOnly = true)
    PostTag get(Post post, Tag tag);

    /**
     * Get all tags of a post.
     *
     * @param post
     *
     * @return
     */
    @Transactional(readOnly = true)
    List<Tag> getTags(Post post);
}
