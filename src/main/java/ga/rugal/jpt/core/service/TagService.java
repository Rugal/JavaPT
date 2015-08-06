package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.entity.Tag;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface TagService
{

    Tag deleteById(Integer id);

    @Transactional(readOnly = true)
    Tag findById(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    Tag save(Tag bean);

    Tag update(Tag bean);

}
