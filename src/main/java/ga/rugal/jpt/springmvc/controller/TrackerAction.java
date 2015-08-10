package ga.rugal.jpt.springmvc.controller;

import com.turn.ttorrent.tracker.Tracker;
import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.common.Message;
import ga.rugal.jpt.common.SystemDefaultProperties;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.springmvc.annotation.Role;
import java.text.MessageFormat;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handlers in this class is only accessible by {@code Admin.Level.SUPER}.
 *
 * @author Rugal Bernstein
 */
@Controller
@RequestMapping(value = "/tracker")
@Role(value = Admin.Level.SUPER)
public class TrackerAction
{

    private static final Logger LOG = LoggerFactory.getLogger(TrackerAction.class.getName());

    @Autowired
    private Tracker tracker;

    /**
     * Start tracker server.
     *
     * @param request
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Message startTracker(HttpServletRequest request)
    {
        if (!tracker.isStop())
        {
            return Message.failMessage(CommonMessageContent.TRACKER_RUNNING);
        }
        //-----------------
        LOG.info(MessageFormat.format(CommonLogContent.SUPER_START_TRACKER,
                                      request.getHeader(SystemDefaultProperties.ID),
                                      request.getRemoteAddr()));
        Message message;
        try
        {
            tracker.start();
            LOG.info(MessageFormat.format(CommonLogContent.TRACKER_STARTED,
                                          request.getHeader(SystemDefaultProperties.ID)));
            message = Message.successMessage(CommonMessageContent.TRACKER_STARTED, null);
        }
        catch (Exception e)
        {
            LOG.error(MessageFormat.format(CommonLogContent.TRACKER_NOT_STARTED,
                                           request.getHeader(SystemDefaultProperties.ID)),
                      e);
            message = Message.failMessage(CommonMessageContent.TRACKER_NOT_STARTED);
        }

        return message;
    }

    /**
     * Stop tracker server.
     *
     * @param request
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public Message stopTracker(HttpServletRequest request)
    {
        if (tracker.isStop())
        {
            return Message.failMessage(CommonMessageContent.TRACKER_NOT_RUNNING);
        }
        LOG.info(MessageFormat.format(CommonLogContent.SUPER_STOP_TRACKER,
                                      request.getHeader(SystemDefaultProperties.ID),
                                      request.getRemoteAddr()));
        Message message;
        try
        {
            tracker.stop();
            LOG.info(MessageFormat.format(CommonLogContent.TRACKER_STOPPED,
                                          request.getHeader(SystemDefaultProperties.ID)));
            message = Message.successMessage(CommonMessageContent.TRACKER_STOPPED, null);
        }
        catch (Exception e)
        {
            LOG.error(MessageFormat.format(CommonLogContent.TRACKER_NOT_STOPPED,
                                           request.getHeader(SystemDefaultProperties.ID)),
                      e);
            message = Message.failMessage(CommonMessageContent.TRACKER_NOT_STOPPED);
        }
        return message;
    }
}
