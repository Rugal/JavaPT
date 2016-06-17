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
     * Extract tracker update information from client request message. Prepare this information bean
     * for future tracker updating.
     *
     * @param bean <p>
     * @return <p>
     * @throws TrackerResponseException
     */
    @Transactional(readOnly = true)
    TrackerUpdateBean generateUpdateBean(ClientRequestMessageBean bean) throws TrackerResponseException;

    String toSHA1(String text);

}
