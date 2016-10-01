package ga.rugal.jpt.core.service.impl;

import config.SystemDefaultProperties;
import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.service.TrackerResponseService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 *
 * @author Administrator
 */
@Slf4j
public class TrackerResponseServiceImplTest extends DBTestBase
{

    private final String SAMPLE = SystemDefaultProperties.SCHEMA;

    @Autowired
    private TrackerResponseService trackerResponseService;

    @Mock
    private HttpServletResponse mockResponse;

    private final MockHttpServletResponse response = new MockHttpServletResponse();

    private ByteBuffer byteBuffer;

    public TrackerResponseServiceImplTest()
    {
    }

    @Before
    public void setUp() throws UnsupportedEncodingException
    {
        LOG.info("setUp");

        MockitoAnnotations.initMocks(this);
        byteBuffer = ByteBuffer.wrap(SAMPLE.getBytes(SystemDefaultProperties.BYTE_ENCODING));
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
    }

    @Test
    public void writeResponseBuffer_ok() throws UnsupportedEncodingException, IOException
    {
        trackerResponseService.writeResponseBuffer(response, byteBuffer);
        Assert.assertEquals(SAMPLE, response.getContentAsString());
    }

    @Test(expected = IOException.class)
    public void writeResponseBuffer_exception() throws IOException
    {
        Mockito.doNothing().when(mockResponse).setDateHeader(any(), any(Long.class));
        Mockito.doNothing().when(mockResponse).setContentType(any());
        Mockito.doThrow(IOException.class).when(mockResponse).getOutputStream();
        trackerResponseService.writeResponseBuffer(mockResponse, byteBuffer);
    }
}
