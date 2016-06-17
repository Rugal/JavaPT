package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.dao.ThreadDao;
import ga.rugal.jpt.core.entity.Thread;

/**
 *
 * @author Rugal Bernstein
 */
public interface ThreadService extends BaseService<ThreadDao>
{

    Thread update(Thread bean);
}
