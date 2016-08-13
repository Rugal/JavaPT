package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Announce;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface AnnounceDao
{

    Announce delete(Announce bean);

    Announce get(Long id);

    Pagination getPage(int pageNo, int pageSize);

    Announce save(Announce bean);

    Announce updateByUpdater(Updater<Announce> updater);

    /**
     * Find the most recent client announce record by user and torrent. Either User or Post
     * parameters or both could be null, that means to nullify their query in SQL.
     * <p>
     * @param user the user that has reported this announce. If null, then ignore user query.
     * @param post the post that related with target torrent. If null, then ignore post query.
     * <p>
     * @return A client announce object if such record does exist. otherwise return null.
     */
    @Transactional(readOnly = true)
    Announce findLastAnnounce(User user, Post post);
}
