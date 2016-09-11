package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.entity.Tagging;
import ga.rugal.jpt.core.dao.TaggingDao;

/**
 *
 * @author Rugal Bernstein
 */
public interface TaggingService extends BaseService<TaggingDao>
{

    Tagging update(Tagging bean);
}
