package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.core.service.TrackerResponseService;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rugal Bernstein
 */
@Service
public class TrackerResponseServiceImpl implements TrackerResponseService
{

    private static final Logger LOG = LoggerFactory.getLogger(TrackerResponseServiceImpl.class.getName());

    /**
     * {@inheritDoc }
     */
    @Override
    public void writeResponseBuffer(HttpServletResponse response, ByteBuffer buffer) throws IOException
    {
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setDateHeader("Date", System.currentTimeMillis());
        try
        {
            WritableByteChannel channel = Channels.newChannel(response.getOutputStream());
            channel.write(buffer);
        }
        catch (IOException ex)
        {
            LOG.error(CommonLogContent.ERROR_WRITING_RESPONSE, ex);
            throw ex;
        }

    }
}
