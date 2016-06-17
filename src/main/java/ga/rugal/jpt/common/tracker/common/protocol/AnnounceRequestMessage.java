package ga.rugal.jpt.common.tracker.common.protocol;

/**
 * Base interface for announce request messages.
 *
 * <p>
 * This interface must be implemented by all subtypes of announce request messages for the various
 * tracker protocols.
 * </p>
 *
 * @author mpetazzoni
 */
public interface AnnounceRequestMessage
{

    public static final int DEFAULT_NUM_WANT = 50;

    public byte[] getInfoHash();

    public String getHexInfoHash();

    public byte[] getPeerId();

    public String getHexPeerId();

    public int getPort();

    public long getUploaded();

    public long getDownloaded();

    public long getLeft();

    public boolean getCompact();

    public boolean getNoPeerIds();

    public RequestEvent getEvent();

    public String getIp();

    public int getNumWant();
};
