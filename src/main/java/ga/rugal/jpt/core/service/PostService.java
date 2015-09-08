package ga.rugal.jpt.core.service;

import ga.rugal.jpt.common.tracker.common.Torrent;
import ga.rugal.jpt.core.entity.Post;
import java.util.List;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface PostService
{

    Post deleteById(Integer id);

    @Transactional(readOnly = true)
    Post getByID(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    Post save(Post bean);

    Post update(Post bean);

    @Transactional(readOnly = true)
    Post getByTorrent(String infoHash);

    Post save(Post bean, Torrent torrent);

    @Transactional(readOnly = true)
    List getAllTorrentsOnly();

}
