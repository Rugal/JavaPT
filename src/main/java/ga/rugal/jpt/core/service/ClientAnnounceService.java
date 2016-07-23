package ga.rugal.jpt.core.service;

import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.core.dao.ClientAnnounceDao;
import ga.rugal.jpt.core.entity.ClientAnnounce;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface ClientAnnounceService extends BaseService<ClientAnnounceDao>
{

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
     * @param bean <p>
     * @return
     */
    ClientAnnounce announce(TrackerUpdateBean bean);

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
