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

    Tag deleteById(Integer id);

    @Transactional(readOnly = true)
    Tag getByID(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    Tag save(Tag bean);

    Tag updateByUpdater(Updater<Tag> updater);

}
