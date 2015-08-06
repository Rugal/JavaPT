package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.entity.PostTags;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface PostTagsService
{

    PostTags deleteById(Integer id);

    @Transactional(readOnly = true)
    PostTags findById(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    PostTags save(PostTags bean);

    PostTags update(PostTags bean);

}
