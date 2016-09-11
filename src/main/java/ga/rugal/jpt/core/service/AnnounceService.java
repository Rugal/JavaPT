package ga.rugal.jpt.core.service;

import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.core.dao.AnnounceDao;
import ga.rugal.jpt.core.entity.Announce;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface AnnounceService extends BaseService<AnnounceDao>
{

    Announce update(Announce bean);

    /**
     * Announce a update for user.
     *
     * First to log this announce.
     *
     * Secondly query to get the difference between this update and the most recent one.
     *
     * Then to update user profile in db.
     *
     * @param bean
     *
     * @return
     */
    Announce announce(TrackerUpdateBean bean);

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
    Announce findLastAnnounceByUser(User user);

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
    Announce findLastAnnounceByTorrent(Post post);
}
