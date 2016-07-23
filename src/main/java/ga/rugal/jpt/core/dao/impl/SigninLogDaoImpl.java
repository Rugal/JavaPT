package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.core.dao.SigninLogDao;
import ga.rugal.jpt.core.entity.SigninLog;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.HibernateBaseDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Repository
public class SigninLogDaoImpl extends HibernateBaseDao<SigninLog, Integer> implements SigninLogDao
{

    @Override
    protected Class<SigninLog> getEntityClass()
    {
        return SigninLog.class;
    }

}
