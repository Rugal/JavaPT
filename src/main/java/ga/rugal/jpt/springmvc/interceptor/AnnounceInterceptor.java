package ga.rugal.jpt.springmvc.interceptor;

import config.SystemDefaultProperties;
import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.common.tracker.bcodec.BEValue;
import ga.rugal.jpt.common.tracker.bcodec.BEncoder;
import ga.rugal.jpt.common.tracker.server.TrackerResponseException;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.PostService;
import ga.rugal.jpt.core.service.RequestBeanService;
import ga.rugal.jpt.core.service.TrackerResponseService;
import ga.rugal.jpt.core.service.UserService;
import ga.rugal.jpt.springmvc.controller.AnnounceAction;
import static ga.rugal.jpt.springmvc.controller.AnnounceAction.readParameterFromURL;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * In version 0.1, we just use uid+pid with BCrypt to represent credential.
 * <p>
 * I expect to use uid+pid+salt of a user with BCrypt in future version
 * <p>
 * @since 0.1
 * @author Rugal Bernstein
 */
@Component
public class AnnounceInterceptor implements HandlerInterceptor
{

    private static final Logger LOG = LoggerFactory.getLogger(AnnounceInterceptor.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private RequestBeanService requsetBeanService;

    @Autowired
    private TrackerResponseService trackerResponseService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        LOG.trace(request.getQueryString());
        //User validation
        try
        {
            userValidation(request);
            credentialValidation(request);
        }
        catch (TrackerResponseException e)
        {
            deniedResponse(request, response, e.getMessage());
            return false;
        }
        //Credential validation
        return true;
    }

    /**
     * Validate user identity with given credential.<BR>
     * I decided to use torrent information in "post" table as the primary storage. Because any post
     * must have its related torrent file.
     * <p>
     * @param request <p>
     * @throws TrackerResponseException
     */
    private void credentialValidation(HttpServletRequest request) throws TrackerResponseException
    {
        String credential = request.getParameter(SystemDefaultProperties.CREDENTIAL);
        if (null == credential || credential.isEmpty())
        {
            LOG.debug(CommonLogContent.INVALID_CREDENTIAL, request.getRemoteAddr());
            throw new TrackerResponseException(CommonMessageContent.INVALID_CREDENTIAL);
        }
        String infoHash = requsetBeanService.toSHA1(readParameterFromURL(request.getQueryString(), AnnounceAction.INFO_HASH));
        LOG.trace(infoHash);
        Post post = postService.getDAO().getByInfohash(infoHash);
        if (null == post)
        {
            throw new TrackerResponseException(CommonMessageContent.TORRENT_NOT_FOUND);
        }
        String candidate = request.getParameter(SystemDefaultProperties.UID) + post.getPid();
        if (!BCrypt.checkpw(candidate, credential))
        {
            //be sure our real credential must be:
            //uid:pid --> BCrypt
            LOG.warn(MessageFormat.format(CommonLogContent.FRAUD_REQUEST,
                                          request.getRemoteAddr(),
                                          request.getParameter(SystemDefaultProperties.UID),
                                          infoHash));
            throw new TrackerResponseException(CommonMessageContent.INVALID_CREDENTIAL);
        }
    }

    /**
     * Validate the existence of UID.
     *
     * @param request
     *
     * @return
     *
     * @throws TrackerResponseException
     */
    private User userValidation(HttpServletRequest request) throws TrackerResponseException
    {
        //User validation begin
        String uidString = request.getParameter(SystemDefaultProperties.UID);
        if (null == uidString || uidString.isEmpty())
        {
            LOG.debug(CommonLogContent.INVALID_UID, request.getRemoteAddr());
            throw new TrackerResponseException(CommonMessageContent.INVALID_UID);
        }
        Integer uid;
        try
        {
            uid = Integer.parseInt(uidString);
        }
        catch (NumberFormatException e)
        {
            LOG.debug(CommonLogContent.INVALID_UID, request.getRemoteAddr());
            throw new TrackerResponseException(CommonMessageContent.INVALID_UID);
        }
        User user = userService.getDAO().get(uid);
        if (null == user)
        {
            LOG.debug(CommonLogContent.INVALID_UID, request.getRemoteAddr());
            throw new TrackerResponseException(CommonMessageContent.USER_NOT_FOUND);
        }
        //User validation completed
        return user;
    }

    /**
     * This method is just for generating a response with forbidden content.<BR>
     * May throw IOException inside because unable to get response body writer, but this version
     * will shelter it.
     *
     *
     * @param response The response corresponding to the request.
     * @param message
     */
    private void deniedResponse(HttpServletRequest request, HttpServletResponse response, String message)
    {
        response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        try
        {
            Map<String, BEValue> params = new HashMap<>();
            params.put("failure reason", new BEValue(message, SystemDefaultProperties.BYTE_ENCODING));
            ByteBuffer buffer = BEncoder.bencode(params);
            trackerResponseService.writeResponseBuffer(response, buffer);
        }
        catch (IOException ioe)
        {
            LOG.error(ioe.getMessage());
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception
    {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception
    {
    }

}
