package ga.rugal.jpt.common.tracker;

/**
 *
 * @author Rugal Bernstein
 */
public class TrackerResponseException extends Exception
{

    public TrackerResponseException(String bad_client_event)
    {
        super(bad_client_event);
    }

}
