package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.ClientAnnounce;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.UserService;
import ml.rugal.sshcommon.hibernate.Updater;
import ml.rugal.sshcommon.page.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
@Service
@Transactional
public class UserServiceImpl implements UserService
{

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private UserDao dao;

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public boolean isEmailAvailable(String email)
    {
        return dao.isEmailAvailable(email);
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public boolean isUserNameAvailable(String username)
    {
        return dao.isUserNameAvailable(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize)
    {
        return dao.getPage(pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public User getByID(Integer id)
    {
        return dao.getByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean authenticateUser(Integer uid, String password)
    {
        return dao.authenticateUser(uid, password);
    }

    @Override
    public User save(User bean)
    {
        return dao.save(bean);
    }

    @Override
    public User deleteById(Integer id)
    {
        return dao.deleteById(id);
    }

    @Override
    public User update(User bean)
    {
        Updater<User> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

    @Override
    public User clientAnnounce(User bean, ClientAnnounce clientAnnounce)
    {
        bean.setDownloadByte(bean.getDownloadByte() + clientAnnounce.getDownloadByte());
        bean.setUploadByte(bean.getUploadByte() + clientAnnounce.getUploadByte());
        Updater<User> updater = new Updater<>(bean);
        updater.setUpdateMode(Updater.UpdateMode.MIN);
        updater.include("uploadByte");
        updater.include("downloadByte");
        return dao.updateByUpdater(updater);
    }

}
