package ga.rugal.jpt.springmvc.controller;

import ml.rugal.sshcommon.springmvc.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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
     * @param ip
     * @param event
     * @param numwant
     * @param no_peer_id
     * @param trackerid
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/announce", method = RequestMethod.GET)
    public Message clientRequest(@RequestParam byte[] info_hash, @RequestParam byte[] peer_id, @RequestParam int port,
                                 @RequestParam long downloaded, @RequestParam long uploaded, @RequestParam long left,
                                 @RequestParam int compact, @RequestParam int no_peer_id, @RequestParam String event,
                                 @RequestParam(required = false) String ip, @RequestParam(defaultValue = "50") long numwant,
                                 @RequestParam String key, @RequestParam(required = false) String trackerid
    )
    {
        return null;
    }
}
