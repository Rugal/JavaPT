package ga.rugal.jpt.common.tracker.common;

import ga.rugal.jpt.common.tracker.common.protocol.RequestEvent;
import ga.rugal.jpt.core.service.ClientService;
import ga.rugal.jpt.core.service.UserService;
import java.nio.ByteBuffer;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This is a JavaBean for updating torrents and peers.
 * Just one JavaBean includes all properties needed.
 * <p>
 * Actually in 0.1 version, info_hash and peer_id fields need to be injected separately.
 *
 * @author Rugal Bernstein
 * @since 0.1
 */
public class ClientRequestMessageBean
{

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    private String event;

    @NotNull
    private String info_hash;

    @NotNull
    private String peer_id;

    private String corrupt;

    private int port;

    private long downloaded = 0;

    private long uploaded = 0;

    private long left = 0;

    private int compact = 1;

    private String key;

    private int no_peer_id;

    private String ip;

    private int numwant = 50;

    private String trackerid;

    private int userID;

    public ClientRequestMessageBean()
    {
    }

    public String getEvent()
    {
        return event;
    }

    public void setEvent(String event)
    {
        this.event = event;
    }

    public ByteBuffer getBufferPeerId()
    {
        return ByteBuffer.wrap(peer_id.getBytes());
    }

    public String getInfoHash()
    {
        return info_hash;
    }

    public String getPeerId()
    {
        return peer_id;
    }

    public byte[] getByteInfoHash()
    {
        return info_hash.getBytes();
    }

    public void setInfo_hash(String info_hash)
    {
        this.info_hash = info_hash;
    }

    public byte[] getBytePeerId()
    {
        return peer_id.getBytes();
    }

    public void setPeer_id(String peer_id)
    {
        this.peer_id = peer_id;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public long getDownloaded()
    {
        return downloaded;
    }

    public void setDownloaded(long downloaded)
    {
        this.downloaded = downloaded;
    }

    public long getUploaded()
    {
        return uploaded;
    }

    public void setUploaded(long uploaded)
    {
        this.uploaded = uploaded;
    }

    public long getLeft()
    {
        return left;
    }

    public void setLeft(long left)
    {
        this.left = left;
    }

    public int getCompact()
    {
        return compact;
    }

    public void setCompact(int compact)
    {
        this.compact = compact;
    }

    public int getNoPeerId()
    {
        return no_peer_id;
    }

    public void setNo_peer_id(int no_peer_id)
    {
        this.no_peer_id = no_peer_id;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public int getNumwant()
    {
        return numwant;
    }

    public void setNumwant(int numwant)
    {
        this.numwant = numwant;
    }

    public String getTrackerid()
    {
        return trackerid;
    }

    public void setTrackerid(String trackerid)
    {
        this.trackerid = trackerid;
    }

    public String getCorrupt()
    {
        return corrupt;
    }

    public void setCorrupt(String corrupt)
    {
        this.corrupt = corrupt;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public int getUserID()
    {
        return userID;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
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

    public TrackerUpdateBean generate()
    {
        TrackerUpdateBean bean = new TrackerUpdateBean();
        bean.setCompact(compact);
        bean.setDownloaded(downloaded);
        bean.setUploaded(uploaded);
        bean.setLeft(left);
        bean.setIp(ip);
        bean.setKey(key);
        bean.setNo_peer_id(no_peer_id);
        bean.setNumwant(numwant);
        bean.setPort(port);
        bean.setTrackerid(trackerid);
        bean.setEvent(RequestEvent.getByName(event));
        //special SHA1 process
        bean.setInfoHash(toSHA1(info_hash));
        //client information from peer_id field
//        this.peer_id;
        String[] peerIdSplit = this.peer_id.split("-");
        if (null == peerIdSplit || peerIdSplit.length != 3)
        {
            //throw
        }
        bean.setCname(peerIdSplit[1].substring(0, 2));
        bean.setCname(peerIdSplit[1].substring(2));
        bean.setRandom(toSHA1(peerIdSplit[2]));
        //Do database search for user and client
        return bean;
    }

}
