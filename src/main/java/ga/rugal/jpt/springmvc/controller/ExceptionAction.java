package ga.rugal.jpt.springmvc.controller;

import ml.rugal.sshcommon.springmvc.controller.BaseExceptionAction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 *
 * @author Rugal Bernstein
 */
@ControllerAdvice
@Controller
public class ExceptionAction extends BaseExceptionAction
{

    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test()
    {
        return "Cool";
    }
}
