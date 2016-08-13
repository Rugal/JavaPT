package ga.rugal.jpt.core.service.impl;

import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.core.dao.AnnounceDao;
import ga.rugal.jpt.core.entity.Announce;
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
public class AnnounceServiceImpl implements ClientAnnounceService
{

    @Autowired
    private AnnounceDao dao;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    private Announce save(TrackerUpdateBean bean)
    {
        Announce clientAnnounce = new Announce();
        clientAnnounce.setDownload(bean.getDownloaded());
        clientAnnounce.setUpload(bean.getUploaded());
        clientAnnounce.setLeft(bean.getLeft());
        clientAnnounce.setAnnounceTime(System.currentTimeMillis());
        clientAnnounce.setUser(bean.getUser());
        clientAnnounce.setClient(bean.getClient());
        clientAnnounce.setPost(postService.getDAO().getByTorrent(bean.getInfoHash()));
        return dao.save(clientAnnounce);
    }

    @Override
    public Announce update(Announce bean)
    {

        Updater<Announce> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
        //-----These comments is here for testing transaction consistency.-------
//        throw new RuntimeException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Announce announce(TrackerUpdateBean bean)
    {
        //check last update for this torrent
        Announce last = this.dao.findLastAnnounce(bean.getUser(), bean.getPost());
        //log the most recent Client Announce
        Announce current = this.save(bean);
        //compute the difference
        Announce diff = new Announce();
        if (null != last)
        {
            diff.setDownload(current.getDownload() - last.getDownload());
            diff.setUpload(current.getUpload() - last.getUpload());
        }
        //update user information
        userService.announce(bean.getUser(), diff);
        return current;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Announce findLastAnnounceByUser(User user)
    {
        return this.dao.findLastAnnounce(user, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Announce findLastAnnounceByTorrent(Post post)
    {
        return this.dao.findLastAnnounce(null, post);
    }

    @Override
    public AnnounceDao getDAO()
    {
        return this.dao;
    }

}
