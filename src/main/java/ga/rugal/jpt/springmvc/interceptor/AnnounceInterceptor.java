package ga.rugal.jpt.springmvc.interceptor;

import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.common.SystemDefaultProperties;
import ga.rugal.jpt.common.tracker.bcodec.BEValue;
import ga.rugal.jpt.common.tracker.bcodec.BEncoder;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.UserService;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Rugal Bernstein
 */
@Component
public class AnnounceInterceptor implements HandlerInterceptor
{

    private static final Logger LOG = LoggerFactory.getLogger(AnnounceInterceptor.class.getName());

    private static final String UID = "uid";

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        LOG.trace(request.getQueryString());

        String uidString = request.getParameter(UID);
        if (null == uidString || uidString.isEmpty())
        {
            LOG.debug(CommonLogContent.INVALID_FORMAT_UID, request.getRemoteAddr());
            deniedResponse(request, response, CommonMessageContent.INVALID_UID);
            return false;
        }
        Integer uid;
        try
        {
            uid = Integer.parseInt(uidString);
        }
        catch (NumberFormatException e)
        {
            LOG.debug(CommonLogContent.INVALID_FORMAT_UID, request.getRemoteAddr());
            deniedResponse(request, response, CommonMessageContent.INVALID_UID);
            return false;
        }
        User user = userService.getByID(uid);
        if (null == user)
        {
            LOG.debug(CommonLogContent.INVALID_FORMAT_UID, request.getRemoteAddr());
            deniedResponse(request, response, CommonMessageContent.USER_NOT_FOUND);
        }
        return true;
    }

    /**
     * This method is just for generating a response with forbidden content.<BR>
     * May throw IOException inside because unable to get response body writer,
     * but this version will shelter it.
     *
     *
     * @param response The response corresponding to the request.
     * @param message
     */
    private void deniedResponse(HttpServletRequest request, HttpServletResponse response, String message)
    {
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setDateHeader("Date", System.currentTimeMillis());
        response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        try
        {
            WritableByteChannel channel = Channels.newChannel(response.getOutputStream());
            Map<String, BEValue> params = new HashMap<>();
            params.put("failure reason", new BEValue(message, SystemDefaultProperties.BYTE_ENCODING));
            channel.write(BEncoder.bencode(params));
        }
        catch (IOException ioe)
        {
            LOG.error(ioe.getMessage());
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {
    }

}
