package ga.rugal.jpt.core.dao;

import ga.rugal.jpt.core.entity.Tag;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;

/**
 *
 * @author Rugal Bernstein
 */
public interface TagDao
{

    Tag deleteById(Integer id);

    Tag findById(Integer id);

    Pagination getPage(int pageNo, int pageSize);

    Tag save(Tag bean);

    Tag updateByUpdater(Updater<Tag> updater);

}
