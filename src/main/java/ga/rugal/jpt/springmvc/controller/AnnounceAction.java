package ga.rugal.jpt.springmvc.controller;

import ga.rugal.jpt.common.SystemDefaultProperties;
import ga.rugal.jpt.common.tracker.bcodec.BEValue;
import ga.rugal.jpt.common.tracker.bcodec.BEncoder;
import ga.rugal.jpt.common.tracker.common.Peer;
import ga.rugal.jpt.common.tracker.common.protocol.RequestEvent;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Rugal Bernstein
 */
@Controller
public class AnnounceAction
{

    private static final Logger LOG = LoggerFactory.getLogger(AnnounceAction.class.getName());

//    @Autowired
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
    @RequestMapping(value = "/announce", method = RequestMethod.GET)
    public void clientRequest(@RequestParam byte[] info_hash, @RequestParam byte[] peer_id, @RequestParam int port,
                              @RequestParam long downloaded, @RequestParam long uploaded, @RequestParam long left,
                              @RequestParam(defaultValue = "0") int compact, @RequestParam int no_peer_id,
                              @RequestParam(required = false) String event, @RequestParam(defaultValue = "50") long numwant,
                              @RequestParam String key, @RequestParam(required = false) String trackerid,
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
            bean.setEvent(RequestEvent.valueOf(event.toUpperCase()));
            bean.setKey(key);
            bean.setTrackerid(trackerid);
        }
        catch (Exception e)
        {
            throw new Exception("Bad Client event");
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
        if (bean.getEvent() != null && torrent.containsKey(peerId)
            && RequestEvent.STARTED != bean.getEvent())
        {
            //send error
            throw new Exception("Bad client event");
        }
        // When no event is specified, it's a periodic update while the client
        // is operating. If we don't have a peer for this announce, it means
        // the tracker restarted while the client was running. Consider this
        // announce request as a 'started' event.
        if ((bean.getEvent() == null || RequestEvent.NONE == bean.getEvent())
            && torrent.containsKey(peerId))
        {
            bean.setEvent(RequestEvent.STARTED);
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
        WritableByteChannel channel = Channels.newChannel(response.getOutputStream());
        ByteBuffer buffer = this.compactResponse(bean, torrent, torrent.getSomePeers(peer));
        channel.write(buffer);
    }

    /**
     * Craft a compact announce response message.
     *
     * @param bean
     * @param torrent
     * @param peer
     * @param interval
     * @param minInterval
     * @param trackerId
     * @param complete
     * @param incomplete
     * @param peers
     *
     * @return
     *
     * @throws java.io.IOException
     * @throws java.io.UnsupportedEncodingException
     */
    public ByteBuffer compactResponse(TrackerUpdateBean bean, TrackedTorrent torrent, List<Peer> peers)
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
