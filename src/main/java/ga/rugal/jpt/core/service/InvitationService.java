package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.entity.Invitation;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface InvitationService
{

    Invitation deleteById(Integer id);

    @Transactional(readOnly = true)
    Invitation findById(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    Invitation save(Invitation bean);

    Invitation update(Invitation bean);

}
