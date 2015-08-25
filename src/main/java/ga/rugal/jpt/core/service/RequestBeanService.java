package ga.rugal.jpt.core.service;

import ga.rugal.jpt.common.tracker.common.ClientRequestMessageBean;
import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.common.tracker.server.TrackerResponseException;

/**
 *
 * @author Rugal Bernstein
 */
public interface RequestBeanService
{

    TrackerUpdateBean generateUpdateBean(ClientRequestMessageBean bean) throws TrackerResponseException;

}
