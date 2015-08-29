package ga.rugal.jpt.springmvc.controller;

import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.common.SystemDefaultProperties;
import ga.rugal.jpt.common.tracker.bcodec.BEValue;
import ga.rugal.jpt.common.tracker.bcodec.BEncoder;
import ga.rugal.jpt.common.tracker.common.ClientRequestMessageBean;
import ga.rugal.jpt.common.tracker.common.Peer;
import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.common.tracker.server.TrackedPeer;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.common.tracker.server.Tracker;
import ga.rugal.jpt.core.service.RequestBeanService;
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

    @Autowired
    private RequestBeanService requestBeanService;

    private static final String INFO_HASH = "info_hash";

    private static final String PEER_ID = "peer_id";

    /**
     * Support Azureus-style peer id.
     *
     * @param bean     A JavaBean that includes all required information.
     * @param uid
     * @param request
     * @param response
     *
     * @throws java.lang.Exception throw exceptions so that ExceptionHandler could deal with.
     */
    @RequestMapping(value = "/announce", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void announce(@Valid @ModelAttribute ClientRequestMessageBean bean,
                         HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        bean.setIp(request.getRemoteAddr());
        bean.setInfo_hash(getParamater(request.getQueryString(), INFO_HASH));
        bean.setPeer_id(getParamater(request.getQueryString(), PEER_ID));

        LOG.trace(CommonLogContent.START_GENERATE);
        TrackerUpdateBean trackerUpdateBean = requestBeanService.generateUpdateBean(bean);
        //Set this bean as null deliberately to enforce use trackerUpdateBean object only
        bean = null;
        //
        //
        //-------After get formated tracker update bean, starts use tracker update bean only--------
        LOG.debug(CommonLogContent.THE_REQUESTED_INFO, trackerUpdateBean.getUid(), trackerUpdateBean.getInfoHash());

        //TODO Link user by UID, link torrent by info_hash
        LOG.trace(CommonLogContent.START_UPDATE, trackerUpdateBean.getUid());
        // Update the torrent according to the announce event
        TrackedPeer peer = tracker.update(trackerUpdateBean);

        //Generate response content for normal request
        LOG.trace(CommonLogContent.MAKE_RESPONSE, trackerUpdateBean.getUid());
        TrackedTorrent torrent = tracker.get(trackerUpdateBean.getInfoHash());
        ByteBuffer buffer = this.compactResponse(torrent, peer);
        //Some setting for normal response
        writeBuffer(response, buffer);
    }

    /**
     * Get request parameter from query string.
     * <p>
     * This method will separate parameters into map before get the real value. This might be a
     * performance leak point.
     * <p>
     * @param text
     * @param name
     *             <p>
     * @return
     */
    private String getParamater(String text, String name)
    {
        for (String pair : text.split("&"))
        {
            String[] keyval = pair.split("[=]", 2);
            if (keyval[0].equals(name))
            {
                return keyval[1];
            }
        }
        return null;
    }

    /**
     * For better code reusability, use this small method to wrap buffer writing.
     * <p>
     * @param response the response object to write in
     * @param buffer   content to write into response body
     * <p>
     * @throws IOException
     */
    private void writeBuffer(HttpServletResponse response, ByteBuffer buffer) throws IOException
    {
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setDateHeader("Date", System.currentTimeMillis());
        WritableByteChannel channel = Channels.newChannel(response.getOutputStream());
        channel.write(buffer);
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
    private ByteBuffer compactResponse(TrackedTorrent torrent, TrackedPeer peer)
        throws IOException, UnsupportedEncodingException
    {
        Map<String, BEValue> response = new HashMap<>();
        response.put("interval", new BEValue(torrent.getAnnounceInterval()));
        response.put("min interval", new BEValue(SystemDefaultProperties.MIN_REANNOUNCE_INTERVAL));
        response.put("tracker id", new BEValue(SystemDefaultProperties.TRACKER_VERSION));
        response.put("complete", new BEValue(torrent.seeders()));
        response.put("incomplete", new BEValue(torrent.leechers()));

        List<Peer> peers = torrent.getSomePeers(peer);

        //peers number might be 0 at the time that this tracker just started
        if (!peers.isEmpty())
        {
            ByteBuffer data = ByteBuffer.allocate(peers.size() * 6);
            for (Peer p : peers)
            {
                byte[] ip = p.getRawIp();
                if (ip == null || ip.length != 4)
                {
                    continue;
                }
                data.put(ip);
                data.putShort((short) p.getPort());
            }
            response.put("peers", new BEValue(data.array()));
        }
        return BEncoder.bencode(response);
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
        LOG.debug(ex.getMessage());
        response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        Map<String, BEValue> params = new HashMap<>();
        try
        {
            params.put("failure reason", new BEValue(ex.getMessage(), SystemDefaultProperties.BYTE_ENCODING));
            ByteBuffer buffer = BEncoder.bencode(params);
            writeBuffer(response, buffer);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
        }
    }

}
