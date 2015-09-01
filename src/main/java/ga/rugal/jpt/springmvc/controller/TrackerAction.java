package ga.rugal.jpt.springmvc.controller;

import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.common.SystemDefaultProperties;
import ga.rugal.jpt.common.tracker.server.Tracker;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.springmvc.annotation.Role;
import java.text.MessageFormat;
import javax.servlet.http.HttpServletRequest;
import ml.rugal.sshcommon.springmvc.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handlers within this class are only accessible by SUPER admin level.
 * <p>
 * Control tracker server to start or stop by HTTP request.
 * <p>
 * @author Rugal Bernstein
 */
@Controller
@RequestMapping(value = "/tracker")
@Role(Admin.Level.SUPER)
public class TrackerAction
{

    private static final Logger LOG = LoggerFactory.getLogger(TrackerAction.class.getName());

//    @Autowired
    private Tracker tracker;

    /**
     * Start tracker service only by a super administrator.
     *
     * @param request
     *                <p>
     * @return
     *
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Message start(HttpServletRequest request)
    {
        Message message;
        if (tracker.isRunning())
        {
            message = Message.failMessage(CommonMessageContent.TRACKER_RUNNING);
        }
        else
        {
            try
            {
                LOG.info(CommonLogContent.SUPER_START_TRACKER, request.getHeader(SystemDefaultProperties.ID), request.getRemoteAddr());
                tracker.start();
                LOG.info(CommonLogContent.TRACKER_STARTED, request.getHeader(SystemDefaultProperties.ID));
                message = Message.successMessage(CommonMessageContent.TRACKER_STARTED, null);
            }
            catch (Exception e)
            {
                LOG.error(MessageFormat.format(CommonLogContent.TRACKER_NOT_STARTED, request.getHeader(SystemDefaultProperties.ID)), e);
                message = Message.failMessage(CommonMessageContent.TRACKER_NOT_STARTED);
            }
        }
        return message;
    }

    /**
     * Stop the tracker server if possible.
     *
     *
     * @param request
     *                <p>
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public Message stop(HttpServletRequest request)
    {
        Message message;
        if (!tracker.isRunning())
        {
            message = Message.failMessage(CommonMessageContent.TRACKER_NOT_RUNNING);
        }
        else
        {
            try
            {
                LOG.info(CommonLogContent.SUPER_STOP_TRACKER, request.getHeader(SystemDefaultProperties.ID), request.getRemoteAddr());
                tracker.stop();
                LOG.info(CommonLogContent.TRACKER_STOPPED, request.getHeader(SystemDefaultProperties.ID));
                message = Message.successMessage(CommonMessageContent.TRACKER_STOPPED, null);
            }
            catch (Exception e)
            {
                LOG.error(MessageFormat.format(CommonLogContent.TRACKER_NOT_STOPPED, request.getHeader(SystemDefaultProperties.ID)), e);
                message = Message.failMessage(CommonMessageContent.TRACKER_NOT_STOPPED);
            }
        }
        return message;
    }

}
