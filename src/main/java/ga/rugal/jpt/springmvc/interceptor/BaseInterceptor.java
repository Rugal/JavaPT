package ga.rugal.jpt.springmvc.interceptor;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 *
 * @author Rugal Bernstein
 */
public abstract class BaseInterceptor implements HandlerInterceptor
{

    protected final Gson gson = new Gson();

    /**
     * Use this method to produce response content when user access is denied because of some specific reason.
     *
     * Write content in response body.
     *
     * @param response
     */
    protected abstract void deniedResponse(HttpServletResponse response);

}
