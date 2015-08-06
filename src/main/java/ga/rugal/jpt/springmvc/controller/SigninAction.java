package ga.rugal.jpt.springmvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This class is for sign in logging.
 *
 * @author Rugal Bernstein
 */
@Controller
@RequestMapping(value = "/signin")
public class SigninAction
{

    private static final Logger LOG = LoggerFactory.getLogger(SigninAction.class.getName());

}
