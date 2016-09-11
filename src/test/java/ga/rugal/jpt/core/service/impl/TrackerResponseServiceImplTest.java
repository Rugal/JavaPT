package ga.rugal.jpt.core.service.impl;

import config.SystemDefaultProperties;
import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.service.TrackerResponseService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 *
 * @author Administrator
 */
public class TrackerResponseServiceImplTest extends DBTestBase
{

    @Autowired
    private TrackerResponseService trackerResponseService;

    private final String SAMPLE = SystemDefaultProperties.SCHEMA;

    private final MockHttpServletResponse response = new MockHttpServletResponse();

    private final ByteBuffer byteBuffer;

    public TrackerResponseServiceImplTest() throws UnsupportedEncodingException
    {
        byteBuffer = ByteBuffer.wrap(SAMPLE.getBytes(SystemDefaultProperties.BYTE_ENCODING));
    }

    @Test
    public void writeResponseBuffer_ok() throws UnsupportedEncodingException, IOException
    {
        trackerResponseService.writeResponseBuffer(response, byteBuffer);
        Assert.assertEquals(SAMPLE, response.getContentAsString());
    }
}
