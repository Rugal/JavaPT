package ga.rugal.jpt.common.tracker.common.protocol;

import ga.rugal.jpt.common.tracker.common.Peer;
import java.util.List;

/**
 * Base interface for announce response messages.
 *
 * <p>
 * This interface must be implemented by all subtypes of announce response
 * messages for the various tracker protocols.
 * </p>
 *
 * @author mpetazzoni
 */
public interface AnnounceResponseMessage
{

    public int getInterval();

    public int getComplete();

    public int getIncomplete();

    public List<Peer> getPeers();
};
