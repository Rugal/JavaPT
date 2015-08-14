package ga.rugal.jpt.common.tracker.server;

import ga.rugal.jpt.common.tracker.common.Torrent;
import ga.rugal.jpt.common.tracker.common.protocol.RequestEvent;
import java.nio.ByteBuffer;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author Rugal Bernstein
 */
public class TrackerUpdateBean
{

    private Torrent torrent;

    private RequestEvent event;

    private TrackedPeer.PeerState state;

    private byte[] info_hash;

    private byte[] peer_id;

    private int port;

    private long downloaded;

    private long uploaded;

    private long left;

    private int compact;

    private int no_peer_id;

    private String ip;

    private long numwant;

    private String key;

    private String trackerid;

    public TrackerUpdateBean()
    {
    }

    public Torrent getTorrent()
    {
        return torrent;
    }

    public void setTorrent(Torrent torrent)
    {
        this.torrent = torrent;
    }

    public RequestEvent getEvent()
    {
        return event;
    }

    public void setEvent(RequestEvent event)
    {
        this.event = event;
    }

    public ByteBuffer getBufferPeerId()
    {
        return ByteBuffer.wrap(peer_id);
    }

    public String getHexInfoHash()
    {
        return new String(Hex.encodeHex(info_hash, false));
    }

    public String getHexPeerId()
    {
        return new String(Hex.encodeHex(peer_id, false));
    }

    public TrackedPeer.PeerState getState()
    {
        return state;
    }

    public void setState(TrackedPeer.PeerState state)
    {
        this.state = state;
    }

    public byte[] getInfoHash()
    {
        return info_hash;
    }

    public void setInfoHash(byte[] info_hash)
    {
        this.info_hash = info_hash;
    }

    public byte[] getBytePeerId()
    {
        return peer_id;
    }

    public void setPeerId(byte[] peer_id)
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

    public void setNoPeerId(int no_peer_id)
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

    public long getNumwant()
    {
        return numwant;
    }

    public void setNumwant(long numwant)
    {
        this.numwant = numwant;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getTrackerid()
    {
        return trackerid;
    }

    public void setTrackerid(String trackerid)
    {
        this.trackerid = trackerid;
    }

}
