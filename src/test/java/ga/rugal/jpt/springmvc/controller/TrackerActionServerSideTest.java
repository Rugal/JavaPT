package ga.rugal.jpt.springmvc.controller;

import ga.rugal.ControllerServerSideTestBase;
import ga.rugal.jpt.common.SystemDefaultProperties;
import java.util.HashMap;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Rugal Bernstein
 */
public class TrackerActionServerSideTest extends ControllerServerSideTestBase
{

    @Autowired
    private TrackerAction trackerAction;

    @Test
//    @Ignore
    public void registerStudent()
    {
        request.setRequestURI("/tracker");
        request.setMethod(HttpMethod.PUT.name());
        request.addHeader(SystemDefaultProperties.ID, "rugal");
        request.addHeader(SystemDefaultProperties.CREDENTIAL, "123456");

//        String json = "{\"id\":\"2\",\"name\":\"tenjin\",\"age\":\"23\"}";
//        request.setContent(json.getBytes());
        Class<?>[] parameterTypes = new Class<?>[]
        {
        };
        ModelAndView mv = null;
        try
        {
            mv = handlerAdapter
                .handle(request, response, new HandlerMethod(trackerAction, "startTracker", parameterTypes));
            System.out.println("Rugal");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void getAddress()
    {
        request.setMethod(HttpMethod.GET.name());
        request.setRequestURI("/student/{id}");
        HashMap<String, String> pathVariablesMap = new HashMap<>(1);
        pathVariablesMap.put("id", "3");
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, pathVariablesMap);
        Class<?>[] parameterTypes = new Class<?>[]
        {
            Integer.class
        };
        ModelAndView mv = null;
        try
        {
            mv = handlerAdapter
                .handle(request, response, new HandlerMethod(trackerAction, "retrieve", parameterTypes));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
