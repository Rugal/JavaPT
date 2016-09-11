package ga.rugal.jpt.core.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rugal Bernstein
 */
public interface TrackerResponseService
{

    /**
     * Write buffer into HTTP response object.
     * <p>
     * For better code reusability, use this small method to wrap buffer writing.
     * <p>
     * @param response
     * @param buffer
     *
     * @throws java.io.IOException when unable to write byte into response channel.
     */
    void writeResponseBuffer(HttpServletResponse response, ByteBuffer buffer) throws IOException;
}
