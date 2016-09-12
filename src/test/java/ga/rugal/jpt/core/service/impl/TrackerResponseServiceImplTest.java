package ga.rugal.jpt.core.service.impl;

import config.SystemDefaultProperties;
import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.service.TrackerResponseService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 *
 * @author Administrator
 */
@Slf4j
public class TrackerResponseServiceImplTest extends DBTestBase
{

    @Autowired
    private TrackerResponseService trackerResponseService;

    private final String SAMPLE = SystemDefaultProperties.SCHEMA;

    private final MockHttpServletResponse response = new MockHttpServletResponse();

    private ByteBuffer byteBuffer;

    public TrackerResponseServiceImplTest()
    {
    }

    @Before
    public void setUp() throws UnsupportedEncodingException
    {
        LOG.info("setUp");
        byteBuffer = ByteBuffer.wrap(SAMPLE.getBytes(SystemDefaultProperties.BYTE_ENCODING));
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
    }

    @Test
    public void writeResponseBuffer() throws UnsupportedEncodingException, IOException
    {
        trackerResponseService.writeResponseBuffer(response, byteBuffer);
        Assert.assertEquals(SAMPLE, response.getContentAsString());
    }
}
