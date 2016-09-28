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

    Post get(Integer id);

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
