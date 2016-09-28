package ga.rugal.jpt.core.service.impl;

import config.SystemDefaultProperties;
import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.service.TrackerResponseService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
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

    @Autowired
    private TrackerResponseService trackerResponseService;

    private final String SAMPLE = SystemDefaultProperties.SCHEMA;

    private final MockHttpServletResponse response = new MockHttpServletResponse();

    @Mock
    private HttpServletResponse mockResponse;

    private ByteBuffer byteBuffer;

    public TrackerResponseServiceImplTest()
    {
    }

    @SneakyThrows
    @Before
    public void setUp() throws UnsupportedEncodingException
    {
        LOG.info("setUp");
        MockitoAnnotations.initMocks(this);
        Mockito.doNothing().when(mockResponse).setContentType(any());
        Mockito.doNothing().when(mockResponse).setDateHeader(any(String.class), any(Long.class));
        Mockito.doThrow(new IOException()).when(mockResponse).getOutputStream();
        byteBuffer = ByteBuffer.wrap(SAMPLE.getBytes(SystemDefaultProperties.BYTE_ENCODING));
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
    }

    @SneakyThrows
    @Test
    public void writeResponseBuffer_ok()
    {
        trackerResponseService.writeResponseBuffer(response, byteBuffer);
        Assert.assertEquals(SAMPLE, response.getContentAsString());
    }

    @SneakyThrows
    @Test(expected = IOException.class)
    public void writeResponseBuffer_exception()
    {
        trackerResponseService.writeResponseBuffer(mockResponse, byteBuffer);
    }
}
