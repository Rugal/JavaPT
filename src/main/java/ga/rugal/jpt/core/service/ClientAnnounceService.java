package ga.rugal.jpt.core.service;

import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.core.entity.ClientAnnounce;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface ClientAnnounceService
{

    ClientAnnounce deleteById(Long id);

    @Transactional(readOnly = true)
    ClientAnnounce getByID(Long id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    ClientAnnounce save(ClientAnnounce bean);

    ClientAnnounce update(ClientAnnounce bean);

    /**
     * Announce a update for user.
     * <p>
     * First to log this announce.
     * <p>
     * Secondly query to get the difference between this update and the most recent one.
     * <p>
     * Then to update user profile in db.
     * <p>
     * @param bean
     *             <p>
     * @return
     */
    ClientAnnounce announce(TrackerUpdateBean bean);

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
    ClientAnnounce findLastAnnounce(User user, Post post);

    /**
     * Find the most recent client announce record according to user.
     * <p>
     * Null in User parameter makes this method identical to {@code findLastAnnounce(null, null)}
     * <p>
     * @param user the user to be query on.
     * <p>
     * @return A client announce object if such record does exist. otherwise return null.
     */
    @Transactional(readOnly = true)
    ClientAnnounce findLastAnnounceByUser(User user);

    /**
     * Find the most recent client announce record according to torrent.
     * <p>
     * Null in Post parameter makes this method identical to {@code findLastAnnounce(null, null)}
     * <p>
     * @param post the post that related with target torrent.
     * <p>
     * @return A client announce object if such record does exist. otherwise return null.
     */
    @Transactional(readOnly = true)
    ClientAnnounce findLastAnnounceByTorrent(Post post);

}
