package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Post;
import java.util.List;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface PostDao
{

    Post delete(Post bean);

    /**
     * Get user object with bencode setting.
     *
     * @param id
     * @param withBencode specifier if Bencode should be included
     *
     * @return
     */
    Post get(Integer pid, boolean withBencode);

    /**
     * Get post object, without Bencode by default.
     *
     * @param pid
     *
     * @return
     */
    Post get(Integer pid);

    Pagination getPage(int pageNo, int pageSize);

    Post save(Post bean);

    Post updateByUpdater(Updater<Post> updater);

    @Transactional(readOnly = true)
    Post getByInfohash(String infoHash);

    /**
     * Get all Torronts. This should only be invoked for tracker initialization.
     *
     * @return
     */
    @Transactional(readOnly = true)
    List getAllTorrentsOnly();
}
