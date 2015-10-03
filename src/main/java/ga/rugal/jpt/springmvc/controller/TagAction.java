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

    private static final File iconFolder = new File(SystemDefaultProperties.ICON_PATH);

    private static final Logger LOG = LoggerFactory.getLogger(TagAction.class.getName());

    @Autowired
    private TagService tagService;

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
            tagService.save(bean);
        }
        catch (IOException ex)
        {
            LOG.error(CommonLogContent.TAG_NOT_SAVED, ex);
            return Message.failMessage(CommonMessageContent.TAG_NOT_SAVED);
        }
        return Message.successMessage(CommonMessageContent.SAVE_TAG, bean);
    }

    private File randomFile(String filename)
    {
        String ext = filename.substring(filename.lastIndexOf(".") + 1);
        String randomName = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(5), ext);
        return new File(iconFolder, randomName);
    }

    private String saveFile(MultipartFile uploadedFile) throws IOException
    {
        File file = this.randomFile(uploadedFile.getOriginalFilename());
        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file)))
        {
            byte[] data = uploadedFile.getBytes();
            LOG.debug("Length of byte array is " + data.length);
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
     * @param uploadedFile new image file uploaded. Must present.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Message updateTag(@PathVariable("id") Integer id,
                             @RequestParam(required = false) String name,
                             @RequestParam("file") MultipartFile uploadedFile)
    {
        Tag dbTag = tagService.getByID(id);
        if (null == dbTag)
        {
            return Message.failMessage(CommonMessageContent.TAG_NOT_FOUND);
        }
        if (null == name)
        {
            name = dbTag.getName();
        }
        Message message = this.saveTag(name, uploadedFile);
        if (message.getStatus().equals(Message.FAIL))
        {
            message.setMessage(CommonMessageContent.TAG_NOT_UPDATED);
        }
        else
        {
            this.deleteTag(id);
            message.setMessage(CommonMessageContent.UPDATE_TAG);
        }
        return message;
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
        Tag bean = tagService.getByID(id);
        if (null == bean)
        {
            return Message.failMessage(CommonMessageContent.TAG_NOT_FOUND);
        }
        this.deleteFile(bean.getIcon());
        tagService.deleteById(id);
        return Message.successMessage(CommonMessageContent.DELETE_TAG, bean);
    }

    private void deleteFile(String filename)
    {
        File file = new File(iconFolder, filename);
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
        Tag bean = tagService.getByID(id);
        Message message;
        if (null == bean)
        {
            message = Message.failMessage(CommonMessageContent.TAG_NOT_FOUND);
        }
        else
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
     * @return
     *
     * @throws java.io.IOException
     */
    @ResponseBody
    @RequestMapping(value = "/{id}/icon", method = RequestMethod.GET, produces =
                {
                    MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE
    })
    public byte[] getTagIcon(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException
    {
        Tag bean = tagService.getByID(id);
        if (null == bean)
        {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            return Message.failMessage(CommonMessageContent.TAG_NOT_FOUND);
            return null;
        }
        File iconFile = new File(iconFolder, bean.getIcon());
        if (!iconFile.exists())
        {
            LOG.error(CommonLogContent.TAG_ICON_NOT_FOUND);
//            return Message.failMessage(CommonMessageContent.TAG_ICON_NOT_FOUND);
            return null;
        }
        byte[] data;
        try
        {
            data = FileCopyUtils.copyToByteArray(iconFile);
        }
        catch (IOException ex)
        {
            LOG.error("I/O exception occurs when reading icon", ex);
            throw ex;
        }
        LOG.debug("Length of byte array is " + data.length);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setContentLength(data.length);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\"", bean.getIcon()));
        return data;
    }
}
