package ga.rugal.jpt.springmvc.controller;

import config.SystemDefaultProperties;
import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.core.entity.Tag;
import ga.rugal.jpt.core.service.TagService;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import ml.rugal.sshcommon.springmvc.util.Message;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Managing class about the tag.<BR>
 * Beware that the parameter name for uploading file is "file".
 *
 * @author Rugal Bernstein
 */
@Controller
@RequestMapping(value = "/tag")
public class TagAction
{

    private static final File ICON_FOLDER = new File(SystemDefaultProperties.ICON_PATH);

    private static final Logger LOG = LoggerFactory.getLogger(TagAction.class.getName());

    @Autowired
    private TagService tagService;

    @Autowired
    private ServletContext context;

    /**
     * Persist a tag bean into database, meanwhile save its image file into file system.<BR>
     * This method will save image into FS before persist tag bean in database.
     *
     * @param name         indicate the name of this tag icon. Will be displayed in UI.
     * @param uploadedFile the byte stream of the uploaded image file.
     *
     * @return The persisted tag bean or error message if unable to save image file.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Message saveTag(@RequestParam(value = "name", required = true) String name,
                           @RequestParam("file") MultipartFile uploadedFile)
    {
        Tag bean = new Tag();
        bean.setName(name);
        try
        {
            String filename = this.saveFile(uploadedFile);
            bean.setIcon(filename);
            tagService.getDAO().save(bean);
        }
        catch (IOException ex)
        {
            LOG.error(CommonLogContent.TAG_NOT_SAVED, ex);
            return Message.failMessage(CommonMessageContent.TAG_NOT_SAVED);
        }
        return Message.successMessage(CommonMessageContent.SAVE_TAG, bean);
    }

    /**
     * Generate random unique name for saving a icon file.<BR>
     * The length of the random name before extension could be determined by a system default
     * property.<BR>
     * Beware that the icon file is always store under
     * {@link config.SystemDefaultProperties#ICON_PATH}
     *
     * @param filename This is the file name that client send to us.
     *
     * @return
     */
    private File randomFile(String filename)
    {
        String ext = filename.substring(filename.lastIndexOf(".") + 1);
        String randomName = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(6), ext);
        return new File(ICON_FOLDER, randomName);
    }

    /**
     * Do the file saving action in real. First is to generate a random file in case of name
     * collision. Second thing is to write data byte into output stream.
     *
     * @param uploadedFile
     *
     * @return
     *
     * @throws IOException
     */
    private String saveFile(MultipartFile uploadedFile) throws IOException
    {
        File file = this.randomFile(uploadedFile.getOriginalFilename());
        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file)))
        {
            byte[] data = uploadedFile.getBytes();
            LOG.trace("Length of byte array is " + data.length);
            stream.write(data);
        }
        catch (IOException ex)
        {
            throw ex;
        }
        return file.getName();
    }

    /**
     * Update a tag bean. Usually is to either change its name or change image.<BR>
     * May unable to update icon, in this case, keep all information of old tag.
     *
     * @param id           primary key of target Tag tid.
     * @param name         new name if exists
     * @param uploadedFile new image file to update if exists.
     *
     * @return
     *
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Message updateTag(@PathVariable("id") Integer id,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "file", required = false) MultipartFile uploadedFile)
    {
        Tag dbTag = tagService.getDAO().getByID(id);
        if (null == dbTag)
        {
            return Message.failMessage(CommonMessageContent.TAG_NOT_FOUND);
        }
        if (null != name)
        {
            dbTag.setName(name);
        }
        if (null != uploadedFile)
        {
            try
            {
                String newFileName = saveFile(uploadedFile);
                dbTag.setIcon(newFileName);
                deleteFile(dbTag.getIcon());
            }
            catch (IOException ex)
            {
                LOG.error(CommonLogContent.ERROR_WRITE_TAG, ex);
                return Message.failMessage(CommonMessageContent.TAG_NOT_UPDATED);
            }
        }
        tagService.update(dbTag);
        return Message.successMessage(CommonMessageContent.UPDATE_TAG, dbTag);
    }

    /**
     * DELETE a tag record from database after deleting its icon file.
     *
     * @param id the target Tag tid.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Message deleteTag(@PathVariable("id") Integer id)
    {
        Tag bean = tagService.getDAO().getByID(id);
        if (null == bean)
        {
            return Message.failMessage(CommonMessageContent.TAG_NOT_FOUND);
        }
        this.deleteFile(bean.getIcon());
        tagService.getDAO().deleteById(id);
        return Message.successMessage(CommonMessageContent.DELETE_TAG, bean);
    }

    /**
     * Delete a file if exists.
     *
     * @param filename
     */
    private void deleteFile(String filename)
    {
        File file = new File(ICON_FOLDER, filename);
        if (file.exists())
        {
            file.delete();
        }
    }

    /**
     * GET a tag record from database without its icon file.
     *
     * @param id the target Tag tid.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Message getTagBean(@PathVariable("id") Integer id)
    {
        Tag bean = tagService.getDAO().getByID(id);
        Message message;
        if (null == bean)
        {
            message = Message.failMessage(CommonMessageContent.TAG_NOT_FOUND);
        } else
        {
            message = Message.successMessage(CommonMessageContent.GET_TAG, bean);
        }
        return message;
    }

    /**
     * GET a tag icon file.
     *
     * @param id       the target Tag tid.
     * @param response
     *
     * @return Give Message object in JSON format if any exception occurs; otherwise, return the
     *         icon data in byte array format.
     *
     */
    @ResponseBody
    @RequestMapping(value = "/{id}/icon", method = RequestMethod.GET)
    public Object getTagIcon(@PathVariable("id") Integer id, HttpServletResponse response)
    {
        Tag bean = tagService.getDAO().getByID(id);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        if (null == bean)
        {
            return Message.failMessage(CommonMessageContent.TAG_NOT_FOUND);
        }
        File iconFile = new File(ICON_FOLDER, bean.getIcon());
        if (!iconFile.exists())
        {
            LOG.error(CommonLogContent.TAG_ICON_NOT_FOUND);
            return Message.failMessage(CommonMessageContent.TAG_ICON_NOT_FOUND);
        }
        byte[] data;
        try
        {
            data = FileCopyUtils.copyToByteArray(iconFile);
        }
        catch (IOException ex)
        {
            LOG.error(CommonLogContent.ERROR_READ_TAG, ex);
            return Message.failMessage(CommonMessageContent.TAG_NOT_READ);
        }
        LOG.trace(CommonLogContent.IMAGE_LENGTH, data.length);
        response.setContentType(context.getMimeType(bean.getIcon()));
        response.setContentLength(data.length);
        //The code below is for in browser displaying
        //response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\"", bean.getIcon()));
        return data;
    }

    /**
     * Find tags by their name. Because the total tags wight not be very big, I design this method
     * to do full table scan.
     *
     * @param partialName partial name of the tag you need.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Message findTagByName(@RequestParam(name = "name") String partialName)
    {
        List<Tag> list = tagService.getDAO().findByName(partialName);
        Message message;
        if (null == list || list.isEmpty())
        {
            message = Message.failMessage(CommonMessageContent.TAG_NOT_FOUND);
        } else
        {
            message = Message.successMessage(CommonMessageContent.GET_TAG, list);
        }
        return message;
    }

}
