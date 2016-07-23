package ga.rugal.jpt.springmvc.controller;

import config.SystemDefaultProperties;
import ga.rugal.jpt.core.entity.Thread;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.ThreadService;
import ga.rugal.jpt.core.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@Slf4j
@Controller
@RequestMapping(value = "/thread")
public class ThreadAction
{

    @Autowired
    private UserService userService;

    @Autowired
    private ThreadService threadService;

    /**
     * Update a thread bean.
     *
     * @param tid      primary key of target thread
     * @param bean     the newer version of post bean
     * @param request
     * @param response
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{tid}", method = RequestMethod.PUT)
    public Object update(@PathVariable("tid") Integer tid, @RequestBody Thread bean,
                         HttpServletRequest request, HttpServletResponse response)
    {
        //-------------Existence check---------------
        Thread dbThread = threadService.getDAO().get(tid);
        if (null == dbThread)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        //------------Permission check---------------
        int uid = Integer.parseInt(request.getHeader(SystemDefaultProperties.ID));
        User user = userService.getDAO().get(uid);
        if (!dbThread.canWrite(user))
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        bean.setTid(tid);
        threadService.update(bean);
        response.setStatus(HttpServletResponse.SC_OK);
        return bean;
    }

    /**
     * DELETE a thread record from database.
     *
     * @param tid      the target thread tid.
     * @param request
     * @param response
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{tid}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable("tid") Integer tid, HttpServletRequest request, HttpServletResponse response)
    {
        //-------------Existence check---------------
        Thread dbThread = threadService.getDAO().get(tid);
        if (null == dbThread)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        //------------Permission check---------------
        int uid = Integer.parseInt(request.getHeader(SystemDefaultProperties.ID));
        User user = userService.getDAO().get(uid);
        if (!dbThread.canWrite(user))
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        threadService.getDAO().delete(dbThread);
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        return null;
    }

    /**
     * GET a thread from database.
     *
     * @param tid      primary key of target thread.
     * @param request
     * @param response
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{tid}", method = RequestMethod.GET)
    public Object get(@PathVariable("tid") Integer tid, HttpServletRequest request, HttpServletResponse response)
    {
        //-------------Existence check---------------
        Thread dbThread = threadService.getDAO().get(tid);
        if (null == dbThread)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        //------------Permission check---------------
        int uid = Integer.parseInt(request.getHeader(SystemDefaultProperties.ID));
        User user = userService.getDAO().get(uid);
        if (!dbThread.canRead(user))
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return dbThread;
    }

}
