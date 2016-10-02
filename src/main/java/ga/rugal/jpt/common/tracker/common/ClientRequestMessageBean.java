package ga.rugal.jpt.common.tracker.common;

import java.nio.ByteBuffer;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * This bean used to deserialize the information sent from client annonuc.<BR>
 * Both info_hash and peer_id fields need to be injected separately.
 *
 * @author Rugal Bernstein
 * @since 0.1
 */
@Data
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

    public ByteBuffer getBufferPeerId()
    {
        return ByteBuffer.wrap(peer_id.getBytes());
    }

    public byte[] getByteInfoHash()
    {
        return info_hash.getBytes();
    }

    public byte[] getBytePeerId()
    {
        return peer_id.getBytes();
    }
}
