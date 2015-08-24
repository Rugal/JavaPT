package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.common.tracker.server.TrackerUpdateBean;
import ga.rugal.jpt.core.dao.ClientAnnounceDao;
import ga.rugal.jpt.core.entity.ClientAnnounce;
import ga.rugal.jpt.core.service.ClientAnnounceService;
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
public class ClientAnnounceServiceImpl implements ClientAnnounceService
{

    private static final Logger LOG = LoggerFactory.getLogger(ClientAnnounceServiceImpl.class.getName());

    @Autowired
    private ClientAnnounceDao dao;

    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize)
    {
        return dao.getPage(pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientAnnounce getByID(Long id)
    {
        return dao.getByID(id);
    }

    @Override
    public ClientAnnounce save(ClientAnnounce bean)
    {
        return dao.save(bean);
        //-----These comments is here for testing transaction consistency.-------
//        dao.save(bean);
//        this.update(bean);
//        return bean;
    }

    @Override
    public ClientAnnounce save(TrackerUpdateBean bean)
    {
        ClientAnnounce clientAnnounce = new ClientAnnounce();
        clientAnnounce.setDownloadByte(bean.getDownloaded());
        clientAnnounce.setUploadByte(bean.getUploaded());
        clientAnnounce.setLeftByte(bean.getLeft());
        clientAnnounce.setAnnounceTime(System.currentTimeMillis());
        clientAnnounce.setUid(userService.getByID(bean.getUid()));
        return dao.save(clientAnnounce);
    }

    @Override
    public ClientAnnounce deleteById(Long id)
    {
        return dao.deleteById(id);
    }

    @Override
    public ClientAnnounce update(ClientAnnounce bean)
    {

        Updater<ClientAnnounce> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
        //-----These comments is here for testing transaction consistency.-------
//        throw new RuntimeException();
    }

}
