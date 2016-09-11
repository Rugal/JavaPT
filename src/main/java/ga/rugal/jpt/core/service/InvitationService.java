package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.dao.InvitationDao;
import ga.rugal.jpt.core.entity.Invitation;

/**
 *
 * @author Rugal Bernstein
 */
public interface InvitationService extends BaseService<InvitationDao>
{

    Invitation update(Invitation bean);
}
