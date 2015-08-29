package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.common.tracker.common.ClientRequestMessageBean;
import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.common.tracker.common.protocol.RequestEvent;
import ga.rugal.jpt.common.tracker.server.TrackerResponseException;
import ga.rugal.jpt.core.entity.Client;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.ClientService;
import ga.rugal.jpt.core.service.RequestBeanService;
import ga.rugal.jpt.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use this service class for better decoupling.
 * <p>
 * Separate TrackerUpdateBean's functionality with RequestMessageBean
 * <p>
 * @author Rugal Bernstein
 */
@Service
@Transactional
public class RequestBeanServiceImpl implements RequestBeanService
{

    private static final Logger LOG = LoggerFactory.getLogger(RequestBeanServiceImpl.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    /**
     * {@inheritDoc }
     * <p>
     * @param b
     *          <p>
     * @throws ga.rugal.jpt.common.tracker.server.TrackerResponseException
     */
    @Override
    @Transactional(readOnly = true)
    public TrackerUpdateBean generateUpdateBean(ClientRequestMessageBean b) throws TrackerResponseException
    {
        TrackerUpdateBean bean = new TrackerUpdateBean();
        bean.setCorrupt(b.getCorrupt());
        bean.setCompact(b.getCompact());
        bean.setDownloaded(b.getDownloaded());
        bean.setUploaded(b.getUploaded());
        bean.setLeft(b.getLeft());
        bean.setIp(b.getIp());
        bean.setNo_peer_id(b.getNoPeerId());
        bean.setNumwant(b.getNumwant());
        bean.setPort(b.getPort());
        bean.setTrackerid(b.getTrackerid());
        bean.setEvent(RequestEvent.getByName(b.getEvent()));
        bean.setKey(b.getKey());
        //special SHA1 process
        try
        {
            //any torrent hash info must be in upper case
            bean.setInfoHash(toSHA1(b.getInfoHash()).toUpperCase());
        }
        catch (RuntimeException e)
        {
            throw new TrackerResponseException(CommonMessageContent.INVALID_INFOHASH);
        }
        readPeerID(bean, b.getPeerId());
        //Do database search for user and client
        User user = userService.getByID(b.getUserID());
        if (null == user)
        {
            throw new TrackerResponseException(CommonMessageContent.USER_NOT_FOUND);
        }
        bean.setUser(user);

        //see if reported from allowed Client software.
        Client client = clientService.findByPeerID(bean.getCname(), bean.getVersion());
        if (!client.isEnabled())
        {
            throw new TrackerResponseException(CommonMessageContent.UNSUPPORTED_CLIENT);
        }
        bean.setClient(client);
        return bean;
    }

    /**
     * Extract peer id from percent-encoded format into client name, version and random number. set
     * those data into TrackerUpdateBean.
     * <p>
     * @param bean
     * @param peerID
     *               <p>
     * @throws TrackerResponseException
     */
    private void readPeerID(TrackerUpdateBean bean, String peerID) throws TrackerResponseException
    {
        //client information from peer_id field
        String[] peerIdSplit = peerID.split("-", 3);
        if (null == peerIdSplit || peerIdSplit.length != 3)
        {
            throw new TrackerResponseException(CommonMessageContent.INVALID_PEERID);
        }
        bean.setCname(peerIdSplit[1].substring(0, 2));
        bean.setVersion(peerIdSplit[1].substring(2));
        bean.setRandom(toSHA1(peerIdSplit[2]));//the random is percent-encoded
    }

    /**
     * Convert a Percent-encoded string into a SHA1 string.
     * It is not functional to use {@link javax.servlet.ServletRequest#getParameter} since the
     * percent-encoded info_hash and peer_id will be decoded by Springmvc.
     * <p>
     * Refer to
     * http://stackoverflow.com/questions/5637268/how-do-you-decode-info-hash-information-from-tracker-announce-request
     * <p>
     * http://www.asciitable.com/
     * <p>
     * @param text
     *             <p>
     * @return
     */
    @Override
    public String toSHA1(String text)
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

}
