package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.dao.SigninLogDao;
import ga.rugal.jpt.core.entity.SigninLog;

/**
 *
 * @author Rugal Bernstein
 */
public interface SigninLogService extends BaseService<SigninLogDao>
{

    SigninLog update(SigninLog bean);
}
