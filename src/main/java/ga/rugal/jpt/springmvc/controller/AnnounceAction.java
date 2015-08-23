package ga.rugal.jpt.springmvc.controller;

import ga.rugal.jpt.common.SystemDefaultProperties;
import ga.rugal.jpt.common.tracker.bcodec.BEValue;
import ga.rugal.jpt.common.tracker.bcodec.BEncoder;
import ga.rugal.jpt.common.tracker.common.Peer;
import ga.rugal.jpt.common.tracker.server.TrackedPeer;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.common.tracker.server.Tracker;
import ga.rugal.jpt.common.tracker.server.TrackerUpdateBean;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A simple announce action implemented by Rugal Bernstein.
 * <p>
 * Inspired by ttorent(https://github.com/mpetazzoni/ttorrent)
 * <p>
 * @author Rugal Bernstein
 * @since 0.1
 */
@Controller
public class AnnounceAction
{

    private static final Logger LOG = LoggerFactory.getLogger(AnnounceAction.class.getName());

    @Autowired
    private Tracker tracker;

    private static final String INFO_HASH = "info_hash";

    private static final String PEER_ID = "peer_id";

    /**
     *
     * Some useful references.
     *
     * @param bean     A JavaBean that includes all required information.
     * @param request
     * @param response
     *
     * @throws java.lang.Exception throw exceptions so that ExceptionHandler could deal with.
     */
    @RequestMapping(value = "/announce", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void announce(@Valid @ModelAttribute TrackerUpdateBean bean,
                         HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.trace(request.getQueryString());
        bean.setInfo_hash(toSHA1(getParamater(request.getQueryString(), INFO_HASH)).toUpperCase());
        bean.setPeer_id(toSHA1(getParamater(request.getQueryString(), PEER_ID)).toUpperCase());
        bean.setIp(request.getRemoteAddr());
        // Update the torrent according to the announce event
        TrackedPeer peer = tracker.update(bean);
        //Generate response content for normal request
        TrackedTorrent torrent = tracker.get(bean.getInfoHash());
        ByteBuffer buffer = this.compactResponse(bean, torrent, torrent.getSomePeers(peer));
        //Some setting for normal response
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setDateHeader("Date", System.currentTimeMillis());
        WritableByteChannel channel = Channels.newChannel(response.getOutputStream());
        channel.write(buffer);
    }

    /**
     * Convert a Percent-encoded string into a SHA1 string.
     * http://stackoverflow.com/questions/5637268/how-do-you-decode-info-hash-information-from-tracker-announce-request
     * http://www.asciitable.com/
     * <p>
     * @param text
     *             <p>
     * @return
     */
    private String toSHA1(String text)
    {
        if (null == text || text.isEmpty())
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++)
        {
            if ('%' == text.charAt(i))
            {
                sb.append(text.substring(i + 1, i + 3));
                i += 2;
            }
            else
            {
                sb.append(Integer.toHexString(text.charAt(i)));
            }
        }
        return sb.toString();
    }

    /**
     * Craft a compact normal announce response message from torrent and peers.
     * <p>
     * Notice the peers field are compacted only in 0.1 version.
     *
     * <p>
     *
     * @return
     *
     * @throws java.io.IOException
     * @throws java.io.UnsupportedEncodingException
     */
    private ByteBuffer compactResponse(TrackerUpdateBean bean, TrackedTorrent torrent, List<Peer> peers)
        throws IOException, UnsupportedEncodingException
    {
        Map<String, BEValue> response = new HashMap<>();
        response.put("interval", new BEValue(torrent.getAnnounceInterval()));
        response.put("min interval", new BEValue(SystemDefaultProperties.MIN_REANNOUNCE_INTERVAL));
        response.put("tracker id", new BEValue(SystemDefaultProperties.TRACKER_VERSION));
        response.put("complete", new BEValue(torrent.seeders()));
        response.put("incomplete", new BEValue(torrent.leechers()));

        ByteBuffer data = ByteBuffer.allocate(peers.size() * 6);
        for (Peer peer : peers)
        {
            byte[] ip = peer.getRawIp();
            if (ip == null || ip.length != 4)
            {
                continue;
            }
            data.put(ip);
            data.putShort((short) peer.getPort());
        }
        response.put("peers", new BEValue(data.array()));
        return BEncoder.bencode(response);
    }

    private String getParamater(String text, String name)
    {
        Map<String, String> params = new HashMap<>();
        for (String pair : text.split("&"))
        {
            String[] keyval = pair.split("[=]", 2);
            params.put(keyval[0], keyval[1]);
        }
        return params.get(name);
    }

    /**
     * A exception handler that wrap a exception message into an response with failure reason.
     * <p>
     * @param response
     * @param ex
     */
    @ExceptionHandler(Exception.class)
    public void handleAllException(HttpServletResponse response, Exception ex)
    {
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setDateHeader("Date", System.currentTimeMillis());
        response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        try
        {
            WritableByteChannel channel = Channels.newChannel(response.getOutputStream());
            Map<String, BEValue> params = new HashMap<>();
            params.put("failure reason", new BEValue(ex.getMessage(), SystemDefaultProperties.BYTE_ENCODING));
            channel.write(BEncoder.bencode(params));
        }
        catch (IOException ioe)
        {
            LOG.error(ioe.getMessage());
        }
    }

}
