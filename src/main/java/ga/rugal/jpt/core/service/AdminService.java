package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.dao.AdminDao;
import ga.rugal.jpt.core.entity.Admin;

/**
 *
 * @author Rugal Bernstein
 */
public interface AdminService extends BaseService<AdminDao>
{

    Admin update(Admin bean);
}
