package ga.rugal.jpt.springmvc.controller;

import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.common.Message;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.springmvc.annotation.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Rugal Bernstein
 */
@Controller
@RequestMapping(value = "/tracker")
@Role(value = Admin.Level.INSPECTOR)
public class TrackerAction
{

    private static final Logger LOG = LoggerFactory.getLogger(TrackerAction.class.getName());

    /**
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Message startTracker()
    {

        return Message.successMessage(CommonMessageContent.UPDATE_USER, null);
    }

}
