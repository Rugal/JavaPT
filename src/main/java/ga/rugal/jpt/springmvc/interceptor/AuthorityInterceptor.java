package ga.rugal.jpt.springmvc.interceptor;

import config.SystemDefaultProperties;
import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.AdminService;
import ga.rugal.jpt.core.service.UserService;
import ga.rugal.jpt.springmvc.annotation.Role;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.text.MessageFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ml.rugal.sshcommon.springmvc.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Authorization interceptor that judge if this handler or method is accessible by this user.<BR>
 * Because authentication has been done in {@code AuthenticationInterceptor}, this interceptor will not check identity
 * but query role and privilege from database, then check its accessibility.
 *
 * @author Rugal Bernstein
 * @since 0.1
 */
@Component
public class AuthorityInterceptor extends BaseInterceptor
{

    private static final Logger LOG = LoggerFactory.getLogger(AuthorityInterceptor.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    /**
     * This is a pretty simple implementation.<BR>
     * Some of handlers require specific admin level to invoke. Here this interceptor use annotation to reflect required
     * level before seeing if this user is qualified.<p>
     * Hence request can do nothing about the privileges.
     *
     * @param request
     * @param response
     * @param handler
     *
     * @return
     *
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {

        String id = request.getHeader(SystemDefaultProperties.ID);
        HandlerMethod hm = (HandlerMethod) handler;
        LOG.debug(MessageFormat.format(CommonLogContent.USER_ROLE_ACCESS, id,
                                       getHandlerFullName(hm)));
        boolean status = true;
        if (isAccessible(id, hm))
        {
            LOG.debug(MessageFormat.format(CommonLogContent.USER_ROLE_SUCCEEDED, id,
                                           getHandlerFullName(hm)));
        } else
        {
            status = false;
            deniedResponse(response);
            LOG.warn(MessageFormat.format(CommonLogContent.USER_ROLE_FAILED,
                                          id,
                                          getHandlerFullName(hm)));

        }
        return status;
    }

    private String getHandlerFullName(HandlerMethod hm)
    {
        return hm.getBeanType().getSimpleName() + "." + hm.getMethod().getName();
    }

    /**
     * This method is just for generating a response with forbidden content.<BR>
     * May throw IOException inside because unable to get response body writer, but this version will shelter it.
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
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().print(gson.toJson(Message.failMessage(CommonMessageContent.PERMISSION_DENIED)));

        }
        catch (IOException e)
        {
            LOG.error("Unable to get response writer", e);
        }

    }

    /**
     * check roles of this user and required role of handler.<BR>
     * If this handler require no role, treat this as Permit all. Otherwise, check user role. Only accessible if this
     * user contain specific role that defined in {@code Role} annotation
     *
     * @param id
     * @param hm
     *
     * @return
     */
    private boolean isAccessible(String id, HandlerMethod hm)
    {
        Role r = getAnnotation(hm, Role.class);
        //Permit access by default if require no role
        boolean value = true;
        //-------------------
        if (null != r)
        {
            User user = userService.getDAO().get(Integer.parseInt(id));
            value = adminService.meetAnyAdminLevel(user, r.value());
        }
        return value;
    }

    /**
     * Get annotation for current handler method. From method level if defined, otherwise From class level if defined.
     *
     * @param <A>
     * @param hm
     * @param annotation
     *
     * @return
     */
    private <A extends Annotation> A getAnnotation(HandlerMethod hm, Class<A> annotation)
    {
        A classAnnotation = hm.getBeanType().getAnnotation(annotation);//inherit
        A methodAnnotation = hm.getMethodAnnotation(annotation);//override
        return methodAnnotation != null ? methodAnnotation : classAnnotation;
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
