package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Tag;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface TagDao
{

    Tag delete(Tag bean);

    Tag get(Integer id);

    Pagination getPage(int pageNo, int pageSize);

    Tag save(Tag bean);

    @Transactional(readOnly = true)
    Pagination findByName(String partialName, int pageNo, int pageSize);

    Tag updateByUpdater(Updater<Tag> updater);

}
