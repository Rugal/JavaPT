package ga.rugal.jpt.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Rugal Bernstein
 * @since 0.1
 */
@Controller
public class IndexAction
{

    private static final Logger LOG = LoggerFactory.getLogger(IndexAction.class.getName());

    /**
     * Log sign in action.
     *
     * @param request
     *
     * @return
     */
    @RequestMapping(value = "/")
    public ModelAndView index(HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

}
