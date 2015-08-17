package ga.rugal.jpt.core.entity;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Rugal Bernstein
 */
@Entity
@Table(catalog = "postgres", schema = "jpt", name = "post")
public class Post
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_pid_seq")
    @SequenceGenerator(name = "post_pid_seq", sequenceName = "post_pid_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer pid;

    @Column(length = 50)
    private String title;

    @Column(length = 2147483647)
    private String content;

    @Column(length = 50)
    private String torrent;

    @Column(name = "post_time")
    private Long postTime;

    @Column
    private Integer size;

    @Column
    private Boolean enabled;

    @Column
    private Boolean visible;

    @Transient
    private float rate;

    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    private User uid;

    @OneToMany(mappedBy = "pid")
    private transient List<PostTags> postTagsList;

    @OneToMany(mappedBy = "pid")
    private transient List<Thread> threadList;

    public Post()
    {
    }

    public Post(Integer pid)
    {
        this.pid = pid;
    }

    public float getRate()
    {
        return rate;
    }

    public void setRate(float rate)
    {
        this.rate = rate;
    }

    public Integer getPid()
    {
        return pid;
    }

    public void setPid(Integer pid)
    {
        this.pid = pid;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getTorrent()
    {
        return torrent;
    }

    public void setTorrent(String torrent)
    {
        this.torrent = torrent;
    }

    public Long getPostTime()
    {
        return postTime;
    }

    public void setPostTime(Long postTime)
    {
        this.postTime = postTime;
    }

    public Integer getSize()
    {
        return size;
    }

    public void setSize(Integer size)
    {
        this.size = size;
    }

    public Boolean getEnabled()
    {
        return enabled;
    }

    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

    public Boolean getVisible()
    {
        return visible;
    }

    public void setVisible(Boolean visible)
    {
        this.visible = visible;
    }

    public User getUid()
    {
        return uid;
    }

    public void setUid(User uid)
    {
        this.uid = uid;
    }

    public List<PostTags> getPostTagsList()
    {
        return postTagsList;
    }

    public void setPostTagsList(List<PostTags> postTagsList)
    {
        this.postTagsList = postTagsList;
    }

    public List<Thread> getThreadList()
    {
        return threadList;
    }

    public void setThreadList(List<Thread> threadList)
    {
        this.threadList = threadList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (pid != null ? pid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Post))
        {
            return false;
        }
        Post other = (Post) object;
        return !((this.pid == null && other.pid != null) || (this.pid != null && !this.pid.equals(other.pid)));
    }

    @Override
    public String toString()
    {
        return "ga.rugal.jpt.core.entity.Post[ pid=" + pid + " ]";
    }

}
