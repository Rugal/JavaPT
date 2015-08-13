package ga.rugal.jpt.springmvc.controller;

import com.turn.ttorrent.bcodec.BEValue;
import com.turn.ttorrent.bcodec.BEncoder;
import com.turn.ttorrent.common.protocol.TrackerMessage;
import ga.rugal.jpt.common.SystemDefaultProperties;
import ga.rugal.jpt.common.tracker.TrackedPeer;
import ga.rugal.jpt.common.tracker.TrackedTorrent;
import ga.rugal.jpt.common.tracker.Tracker;
import ga.rugal.jpt.common.tracker.TrackerUpdateBean;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Rugal Bernstein
 */
@Controller
public class AnnounceAction
{

    private static final Logger LOG = LoggerFactory.getLogger(AnnounceAction.class.getName());

    @Autowired
    private Tracker tracker;

    /**
     *
     * @param info_hash
     * @param peer_id
     * @param port
     * @param downloaded
     * @param left
     * @param uploaded
     * @param key
     * @param compact
     * @param event
     * @param numwant
     * @param no_peer_id
     * @param trackerid
     * @param request
     * @param response
     *
     * @return
     *
     * @throws java.lang.Exception
     */
    @ResponseBody
    @RequestMapping(value = "/announce", method = RequestMethod.GET)

    public String clientRequest(@RequestParam byte[] info_hash, @RequestParam byte[] peer_id, @RequestParam int port,
                                @RequestParam long downloaded, @RequestParam long uploaded, @RequestParam long left,
                                @RequestParam int compact, @RequestParam int no_peer_id, @RequestParam(required = false) String event,
                                @RequestParam(defaultValue = "50") long numwant, @RequestParam String key,
                                @RequestParam(required = false) String trackerid,
                                HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        TrackerUpdateBean bean = new TrackerUpdateBean();
        try
        {
            bean.setInfoHash(info_hash);
            bean.setPeerId(peer_id);
            bean.setPort(port);
            bean.setDownloaded(downloaded);
            bean.setUploaded(uploaded);
            bean.setLeft(left);
            bean.setCompact(compact);
            bean.setIp(request.getRemoteAddr());
            bean.setNoPeerId(no_peer_id);
            //In case of exception when parsing this value
            bean.setEvent(TrackerMessage.AnnounceRequestMessage.RequestEvent.valueOf(event));
            bean.setKey(key);
            bean.setTrackerid(trackerid);
        }
        catch (Exception e)
        {
        }
        if (!tracker.containsKey(bean.getHexInfoHash()))
        {
            //report there is no such torrent
            throw new Exception("The Requested torrent not found in tracker");
        }
        TrackedTorrent torrent = tracker.get(bean.getHexInfoHash());
        String peerId = bean.getHexPeerId();

        // If an event other than 'started' is specified and we also haven't
        // seen the peer on this torrent before, something went wrong. A
        // previous 'started' announce request should have been made by the
        // client that would have had us register that peer on the torrent this
        // request refers to.
        if (event != null && torrent.getPeer(peerId) == null
            && TrackerMessage.AnnounceRequestMessage.RequestEvent.STARTED != bean.getEvent())
        {
            //send error
            throw new Exception("Bad client event");
        }
        // When no event is specified, it's a periodic update while the client
        // is operating. If we don't have a peer for this announce, it means
        // the tracker restarted while the client was running. Consider this
        // announce request as a 'started' event.
        if ((bean.getEvent() == null || TrackerMessage.AnnounceRequestMessage.RequestEvent.NONE == bean.getEvent())
            && torrent.getPeer(peerId) == null)
        {
            bean.setEvent(TrackerMessage.AnnounceRequestMessage.RequestEvent.STARTED);
        }
        // Update the torrent according to the announce event
        TrackedPeer peer;
        try
        {
            peer = torrent.update(bean);
        }
        catch (IllegalArgumentException iae)
        {
            throw iae;
        }
        return "Rugal";
    }

    @ExceptionHandler(Exception.class)
    public void handleAllException(HttpServletResponse response, Exception ex)
    {
        response.setContentType("text/plain");
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
