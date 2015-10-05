package ga.rugal.jpt.springmvc.controller;

import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.core.entity.Thread;
import ga.rugal.jpt.core.service.ThreadService;
import ml.rugal.sshcommon.springmvc.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Rugal Bernstein
 */
@Controller
@RequestMapping(value = "/thread")
public class ThreadAction
{

    private static final Logger LOG = LoggerFactory.getLogger(ThreadAction.class.getName());

    @Autowired
    private ThreadService threadService;

    /**
     * Update a thread bean.
     *
     * @param tid  primary key of target thread
     * @param bean the newer version of post bean
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{tid}", method = RequestMethod.PUT)
    public Message updateThread(@PathVariable("tid") Integer tid, @RequestBody Thread bean)
    {
        Thread dbThread = threadService.getByID(tid);
        Message message;
        if (null != dbThread)
        {
            bean.setTid(tid);
            threadService.update(bean);
            message = Message.successMessage(CommonMessageContent.UPDATE_THREAD, bean);
        }
        else
        {
            message = Message.failMessage(CommonMessageContent.THREAD_NOT_FOUND);
        }
        return message;
    }

    /**
     * DELETE a thread record from database.
     *
     * @param tid the target thread tid.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{tid}", method = RequestMethod.DELETE)
    public Message deleteThread(@PathVariable("tid") Integer tid)
    {
        Thread bean = threadService.deleteById(tid);
        Message message;
        if (null != bean)
        {
            message = Message.successMessage(CommonMessageContent.DELETE_THREAD, bean);
        }
        else
        {
            message = Message.failMessage(CommonMessageContent.THREAD_NOT_FOUND);
        }
        return message;
    }

    /**
     * GET a thread from database.
     *
     * @param tid primary key of target thread.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{tid}", method = RequestMethod.GET)
    public Message getThread(@PathVariable("tid") Integer tid)
    {
        Thread bean = threadService.getByID(tid);
        Message message;
        if (null != bean)
        {
            message = Message.successMessage(CommonMessageContent.GET_THREAD, bean);
        }
        else
        {
            message = Message.failMessage(CommonMessageContent.THREAD_NOT_FOUND);
        }
        return message;
    }

}
