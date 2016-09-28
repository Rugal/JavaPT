package ga.rugal.jpt.core.service;

import ga.rugal.jpt.common.tracker.common.ClientRequestMessageBean;
import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.common.tracker.server.TrackerResponseException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface RequestBeanService
{

    /**
     * Extract user update information from client request message. Prepare this information for updating tracker later
     * on.
     *
     * @param bean
     *
     * @return
     *
     * @throws TrackerResponseException
     */
    @Transactional(readOnly = true)
    TrackerUpdateBean generateUpdateBean(ClientRequestMessageBean bean) throws TrackerResponseException;

    /**
     * Convert a Percent-encoded string into a SHA1 string. It is not functional to use
     * {@link javax.servlet.ServletRequest#getParameter} since the percent-encoded info_hash and peer_id will be decoded
     * by Springmvc.
     * <p>
     * Refer to
     * http://stackoverflow.com/questions/5637268/how-do-you-decode-info-hash-information-from-tracker-announce-request
     * <p>
     * http://www.asciitable.com/
     * <p>
     * @param text <p>
     * @return
     */
    String toSHA1(String text);

}
