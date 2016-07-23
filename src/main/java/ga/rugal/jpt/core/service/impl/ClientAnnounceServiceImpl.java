package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.core.dao.ClientAnnounceDao;
import ga.rugal.jpt.core.entity.ClientAnnounce;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.ClientAnnounceService;
import ga.rugal.jpt.core.service.PostService;
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
public class ClientAnnounceServiceImpl implements ClientAnnounceService
{

    @Autowired
    private ClientAnnounceDao dao;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    private ClientAnnounce save(TrackerUpdateBean bean)
    {
        ClientAnnounce clientAnnounce = new ClientAnnounce();
        clientAnnounce.setDownloadByte(bean.getDownloaded());
        clientAnnounce.setUploadByte(bean.getUploaded());
        clientAnnounce.setLeftByte(bean.getLeft());
        clientAnnounce.setAnnounceTime(System.currentTimeMillis());
        clientAnnounce.setUser(bean.getUser());
        clientAnnounce.setClient(bean.getClient());
        clientAnnounce.setPost(postService.getDAO().getByTorrent(bean.getInfoHash()));
        return dao.save(clientAnnounce);
    }

    @Override
    public ClientAnnounce update(ClientAnnounce bean)
    {

        Updater<ClientAnnounce> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
        //-----These comments is here for testing transaction consistency.-------
//        throw new RuntimeException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientAnnounce announce(TrackerUpdateBean bean)
    {
        //check last update for this torrent
        ClientAnnounce last = this.dao.findLastAnnounce(bean.getUser(), bean.getPost());
        //log the most recent Client Announce
        ClientAnnounce current = this.save(bean);
        //compute the difference
        ClientAnnounce diff = new ClientAnnounce();
        if (null != last)
        {
            diff.setDownloadByte(current.getDownloadByte() - last.getDownloadByte());
            diff.setUploadByte(current.getUploadByte() - last.getUploadByte());
        }
        //update user information
        userService.clientAnnounce(bean.getUser(), diff);
        return current;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientAnnounce findLastAnnounceByUser(User user)
    {
        return this.dao.findLastAnnounce(user, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientAnnounce findLastAnnounceByTorrent(Post post)
    {
        return this.dao.findLastAnnounce(null, post);
    }

    @Override
    public ClientAnnounceDao getDAO()
    {
        return this.dao;
    }

}
