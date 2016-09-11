package ga.rugal.jpt.springmvc.controller;

import config.SystemDefaultProperties;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.Tag;
import ga.rugal.jpt.core.service.TagService;
import ga.rugal.jpt.springmvc.annotation.Role;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Managing class about the tag.<BR>
 * Beware that the parameter name for uploading file is "file".
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Controller
@RequestMapping(value = "/tag")
public class TagAction
{

    @Autowired
    private TagService tagService;

    //-----------------------------Add Tag--------------------------------------
    /**
     * Persist a tag bean into database.
     *
     * @param bean
     * @param response
     *
     * @return The ID of persisted bean.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @Role(Admin.Role.ADMINISTRATOR)
    public Object save(@RequestBody Tag bean, HttpServletResponse response)
    {
        tagService.getDAO().save(bean);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return bean.getTid();
    }

    //-------------------------------Tag Operations----------------------------------
    /**
     * Update a tag bean.
     *
     * @param id       primary key of target Tag tid.
     * @param name     new name if exists
     * @param response
     *
     *
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @Role(Admin.Role.ADMINISTRATOR)
    public void update(@PathVariable("id") Integer id, @RequestParam(value = "name") String name, HttpServletResponse response)
    {
        Tag dbTag = tagService.getDAO().get(id);
        if (null == dbTag)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (null != name && !dbTag.getName().equals(name))
        {
            dbTag.setName(name);
        }
        tagService.update(dbTag);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    /**
     * DELETE a tag record.
     *
     * @param id       the target Tag tid.
     * @param response
     *
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Role(Admin.Role.ADMINISTRATOR)
    public void delete(@PathVariable("id") Integer id, HttpServletResponse response)
    {
        Tag dbTag = tagService.getDAO().get(id);
        if (null == dbTag)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        tagService.getDAO().delete(dbTag);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    /**
     * GET a tag record.
     *
     * @param id       the target Tag tid.
     * @param response
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable("id") Integer id, HttpServletResponse response)
    {
        Tag dbTag = tagService.getDAO().get(id);
        if (null == dbTag)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        return dbTag;
    }

    /**
     * Find tags by their name.
     *
     * @param partialName partial name of the tag you need.
     * @param pageNo
     * @param pageSize
     * @param response
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Object findByName(@RequestParam(name = "name") String partialName,
                             @RequestParam(name = "pageNo", required = false, defaultValue = SystemDefaultProperties.DEFAULT_PAGE_NUMBER) Integer pageNo,
                             @RequestParam(name = "pageSize", required = false, defaultValue = SystemDefaultProperties.DEFAULT_PAGE_SIZE) Integer pageSize,
                             HttpServletResponse response)
    {
        response.setStatus(HttpServletResponse.SC_OK);
        return tagService.getDAO().findByName(partialName, pageNo, pageSize);
    }
}
