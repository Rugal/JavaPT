package ga.rugal.jpt.core.service;

import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.core.entity.ClientAnnounce;
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
     * First log this announce.
     * <p>
     * Secondly to query get the difference between this update and the most recent one.
     * <p>
     * Then to update information in user profile.
     * <p>
     * @param bean
     *             <p>
     * @return
     */
    void announce(TrackerUpdateBean bean);

}
