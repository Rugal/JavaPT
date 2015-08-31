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
     * Find the most recent client announce record by user and torrent
     * <p>
     * @param user the user that has reported this announce
     * @param post the post that related with target torrent
     * <p>
     * @return A client announce object if such record does exist. otherwise return null.
     */
    @Transactional(readOnly = true)
    ClientAnnounce findLastAnnounce(User user, Post post);

}
