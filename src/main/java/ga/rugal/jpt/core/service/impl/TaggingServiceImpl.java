package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.dao.TaggingDao;
import ga.rugal.jpt.core.service.TaggingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Service
@Transactional
public class TaggingServiceImpl implements TaggingService
{

    @Autowired
    private TaggingDao dao;

    @Override
    public TaggingDao getDAO()
    {
        return this.dao;
    }
}
