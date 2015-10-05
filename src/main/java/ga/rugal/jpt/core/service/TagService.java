package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.entity.Tag;
import java.util.List;
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
    Tag getByID(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    @Transactional(readOnly = true)
    List<Tag> findByName(String partialName);

    Tag save(Tag bean);

    Tag update(Tag bean);

}
