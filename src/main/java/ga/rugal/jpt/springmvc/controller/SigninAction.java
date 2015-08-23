package ga.rugal.jpt.springmvc.controller;

import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.common.SystemDefaultProperties;
import ga.rugal.jpt.core.entity.SigninLog;
import ga.rugal.jpt.core.service.SigninLogService;
import ga.rugal.jpt.core.service.UserService;
import java.text.MessageFormat;
import javax.servlet.http.HttpServletRequest;
import ml.rugal.sshcommon.springmvc.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Rugal Bernstein
 * @since 0.1
 */
@Controller
@RequestMapping(value = "/signin")
public class SigninAction
{

    private static final Logger LOG = LoggerFactory.getLogger(SigninAction.class.getName());

    @Autowired
    private SigninLogService signinLogService;

    @Autowired
    private UserService userService;

    /**
     * Log sign in action.
     *
     * @param request
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Message signin(HttpServletRequest request)
    {
        String id = request.getHeader(SystemDefaultProperties.ID);
        SigninLog signinLog = new SigninLog();
        signinLog.setIp(request.getRemoteAddr());
        signinLog.setUid(userService.getByID(Integer.parseInt(id)));
        signinLogService.save(signinLog);
        LOG.trace(MessageFormat.format(CommonLogContent.SIGNIN, id, request.getRemoteAddr()));
        return Message.successMessage(CommonMessageContent.SIGNIN, signinLog);
    }

}
