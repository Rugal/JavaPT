package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.dao.InvitationDao;
import ga.rugal.jpt.core.entity.Invitation;
import ga.rugal.jpt.core.entity.User;

/**
 *
 * @author Rugal Bernstein
 */
public interface InvitationService extends BaseService<InvitationDao>
{

    Invitation update(Invitation bean);

    /**
     * Generate invitation code and generate it for the invitor.<BR>
     * Will update user credit in the mean time. The credit needed is
     * {@code config.SystemDefaultProperties.INVITATION_CREDIT_NEED}
     *
     * @param invitor
     *
     * @return
     */
    Invitation generate(User invitor);

    /**
     * Consume invitation code.<BR>
     * This will mark the given code as consumed.
     *
     * @param invitee    The user account to be invited.
     * @param invitation
     *
     * @return The invitation code used in this process.
     */
    Invitation consume(User invitee, Invitation invitation);

}
