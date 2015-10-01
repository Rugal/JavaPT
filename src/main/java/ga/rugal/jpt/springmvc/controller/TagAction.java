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
import ml.rugal.sshcommon.springmvc.util.Message;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public Message saveTag(@RequestParam(required = true) String name, @RequestParam("file") MultipartFile uploadedFile)
    {
        Tag bean = new Tag();
        bean.setName(name);
        try
        {
            this.saveFile(uploadedFile);
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

    private void saveFile(MultipartFile uploadedFile) throws IOException
    {
        File file = this.randomFile(uploadedFile.getOriginalFilename());
        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file)))
        {
            stream.write(uploadedFile.getBytes());
        }
        catch (IOException ex)
        {
            throw ex;
        }
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
}
