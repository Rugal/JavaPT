package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.common.tracker.common.ClientRequestMessageBean;
import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.common.tracker.common.protocol.RequestEvent;
import ga.rugal.jpt.common.tracker.server.TrackerResponseException;
import ga.rugal.jpt.core.entity.Client;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.ClientService;
import ga.rugal.jpt.core.service.PostService;
import ga.rugal.jpt.core.service.RequestBeanService;
import ga.rugal.jpt.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Use this service class for better decoupling.
 * <p>
 * Separate TrackerUpdateBean's functionality with RequestMessageBean
 * <p>
 * @author Rugal Bernstein
 */
@Slf4j
@Service
@Transactional
public class RequestBeanServiceImpl implements RequestBeanService
{

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private PostService postService;

    /**
     * {@inheritDoc }
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
        bean.setNo_peer_id(b.getNo_peer_id());
        bean.setNumwant(b.getNumwant());
        bean.setPort(b.getPort());
        bean.setTrackerid(b.getTrackerid());
        bean.setEvent(RequestEvent.getByName(b.getEvent()));
        bean.setKey(b.getKey());
        //unescape to SHA1 process
        try
        {
            //any torrent hash info must be in upper case
            bean.setInfoHash(toSHA1(b.getInfo_hash()));
        }
        catch (RuntimeException e)
        {
            throw new TrackerResponseException(CommonMessageContent.INVALID_INFOHASH);
        }
        //set post by info_hash
        Post post = postService.getDAO().getByInfohash(bean.getInfoHash());
        if (null == post)
        {
            throw new TrackerResponseException(CommonMessageContent.TORRENT_NOT_FOUND);
        }
        bean.setPost(post);
        readPeerID(bean, b.getPeer_id());
        //Do database search for user and client
        User user = userService.getDAO().get(b.getUid());
        if (null == user)
        {
            throw new TrackerResponseException(CommonMessageContent.USER_NOT_FOUND);
        }
        bean.setUser(user);

        //see if reported from allowed Client software.
        Client client = clientService.getDAO().findByPeerID(bean.getCname(), bean.getVersion());
        if (!client.getEnable())
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
     * @param peerID <p>
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
     * {@inheritDoc }
     */
    @Override
    public String toSHA1(String text)
    {
        if (StringUtils.isEmpty(text))
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
            } else
            {
                sb.append(Integer.toHexString(text.charAt(i)));
            }
        }
        return sb.toString().toUpperCase();
    }
}
