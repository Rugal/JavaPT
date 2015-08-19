package ga.rugal.jpt.springmvc.controller;

import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.springmvc.annotation.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Rugal Bernstein
 */
@Controller
@RequestMapping(value = "/admin")
@Role(Admin.Level.ADMIN)
public class AdminAction
{

    private static final Logger LOG = LoggerFactory.getLogger(AdminAction.class.getName());

}
