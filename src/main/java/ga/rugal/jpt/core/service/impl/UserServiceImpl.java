package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.ClientAnnounce;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.hibernate.Updater;
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
public class UserServiceImpl implements UserService
{

    @Autowired
    private UserDao dao;

    @Override
    public UserDao getDAO()
    {
        return this.dao;
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
