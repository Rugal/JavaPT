package ga.rugal.jpt.springmvc.interceptor;

import config.SystemDefaultProperties;
import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.core.service.UserService;
import java.io.IOException;
import java.text.MessageFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * A authentication interceptor than authenticate any matched request by some credential. Store
 * username and credential in request header.
 * <p>
 * Useful when implementing Restful API.
 * <p>
 * @author Rugal Bernstein
 */
@Slf4j
@Component
public class AuthenticationInterceptor extends BaseInterceptor
{

    @Autowired
    private UserService userService;

    /**
     * This interceptor do its jos on all handlers except null null null null null null null null
     * null null null null     {@link ga.rugal.jpt.springmvc.controller.AnnounceAction#announce(ga.rugal.jpt.common.tracker.common.ClientRequestMessageBean, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     * } <BR>
     * Any request that needs authentication must include their
     * {@link ga.rugal.jpt.common.SystemDefaultProperties#ID} and
     * {@link ga.rugal.jpt.common.SystemDefaultProperties#CREDENTIAL} in request header.<p>
     * Example:<BR>
     * curl: <BR> {@code curl -H'id:1' -H'credential:123456'}
     * <p>
     * @param request  The request that has id and credential information in header
     * @param response
     * @param handler  <p>
     * @return true if id and credential match information inside DB, otherwise return false.
     * <p>
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        String id = request.getHeader(SystemDefaultProperties.ID);
        String credential = request.getHeader(SystemDefaultProperties.CREDENTIAL);
        boolean status = true;
        LOG.debug(MessageFormat.format(CommonLogContent.USER_TRY_ACCESS,
                                       id,
                                       request.getRequestURI(),
                                       request.getRemoteAddr()));
        if (!isAuthenticatedUser(id, credential))
        {
            status = false;
            deniedResponse(response);
            LOG.warn(MessageFormat.format(CommonLogContent.USER_ACCESS_FAILED,
                                          id,
                                          credential,
                                          request.getRequestURI(),
                                          request.getRemoteAddr()));
        }
        return status;
    }

    /**
     * This method is just for generating a response with forbidden content.<BR>
     * May throw IOException inside because unable to get response body writer, but this version
     * will shelter it.
     *
     *
     * @param response The response corresponding to the request.
     */
    @Override
    protected void deniedResponse(HttpServletResponse response)
    {
        try
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().flush();
        }
        catch (IOException e)
        {
            LOG.error("Unable to get response writer", e);
        }
    }

    /**
     * This method used put authentication. If you need to check with database, please modify code.
     *
     * @param username   user ID
     * @param credential user password
     *
     * @return true if this user and credential meet requirement, otherwise return false
     */
    private boolean isAuthenticatedUser(String id, String credential)
    {
        boolean isAuthenticated = false;
        try
        {
            isAuthenticated = userService.getDAO().authenticate(Integer.parseInt(id), credential);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage());
        }

        return isAuthenticated;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        LOG.debug(MessageFormat.format(CommonLogContent.USER_ACCESS_SUCCEEDED,
                                       request.getHeader(SystemDefaultProperties.ID),
                                       request.getRequestURI(),
                                       request.getRemoteAddr()));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {
    }

}
