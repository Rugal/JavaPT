package ga.rugal.jpt.common.tracker.common;

import java.nio.ByteBuffer;
import javax.validation.constraints.NotNull;

/**
 * This is a JavaBean that are sent from client. Just one JavaBean includes all properties needed.
 * We will extract information from this bean to generate a formal tracker update bean.
 * <p>
 * Actually in 0.1 version, info_hash and peer_id fields need to be injected separately.
 *
 * @author Rugal Bernstein
 * @since 0.1
 */
public class ClientRequestMessageBean
{

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

    @NotNull
    private String credential;

    private String key;

    private int no_peer_id;

    private String ip;

    private int numwant = 50;

    private String trackerid;

    private int uid;

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

    public String getCredential()
    {
        return credential;
    }

    public void setCredential(String credential)
    {
        this.credential = credential;
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

    public int getUid()
    {
        return uid;
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }
}
