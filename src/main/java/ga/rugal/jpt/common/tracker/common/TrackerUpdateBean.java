package ga.rugal.jpt.common.tracker.common;

import ga.rugal.jpt.common.tracker.common.protocol.RequestEvent;
import ga.rugal.jpt.common.tracker.server.TrackedPeer;
import ga.rugal.jpt.core.entity.Client;
import ga.rugal.jpt.core.entity.Client;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.entity.User;
import java.nio.ByteBuffer;

/**
 *
 * @author Rugal Bernstein
 */
public class TrackerUpdateBean
{

    private TrackedPeer.PeerState state;

    private RequestEvent event;

    private String infoHash;

    private String cname;

    private String corrupt;

    private String version;

    private String random;

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

    private User user;

    private Client client;

    public RequestEvent getEvent()
    {
        return event;
    }

    public void setEvent(RequestEvent event)
    {
        this.event = event;
    }

    public String getCorrupt()
    {
        return corrupt;
    }

    public void setCorrupt(String corrupt)
    {
        this.corrupt = corrupt;
    }

    public ByteBuffer getBufferPeerID()
    {
        return ByteBuffer.wrap(getPeerID().getBytes());
    }

    public String getPeerID()
    {
        return this.getCname() + this.getVersion() + this.getRandom();
    }

    public TrackedPeer.PeerState getState()
    {
        return state;
    }

    public void setState(TrackedPeer.PeerState state)
    {
        this.state = state;
    }

    public String getInfoHash()
    {
        return infoHash;
    }

    public void setInfoHash(String infoHash)
    {
        this.infoHash = infoHash;
    }

    public String getCname()
    {
        return cname;
    }

    public void setCname(String cname)
    {
        this.cname = cname;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getRandom()
    {
        return random;
    }

    public void setRandom(String random)
    {
        this.random = random;
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

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public int getNo_peer_id()
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

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

}
