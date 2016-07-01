package ga.rugal.jpt.core.entity;

import com.google.gson.annotations.Expose;
import config.SystemDefaultProperties;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(schema = "jpt", name = "post")
public class Post extends BaseObject<Post>
{

    private static final String sequence_name = "post_pid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequence_name)
    @SequenceGenerator(name = sequence_name,
        sequenceName = SystemDefaultProperties.SCHEMA + sequence_name, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer pid;

    @Expose
    @Column(length = 50)
    private String title;

    @Expose
    @Column(length = 2147483647)
    private String content;

    @Expose
    @Column(length = 50, name = "info_hash")
    private String infoHash;

    @Expose
    @Column(name = "post_time")
    private Long postTime;

    @Column(length = 2147483647)
    @Expose
    private byte[] bencode;

    @Column
    @Expose
    private Integer size;

    @Column
    @Expose
    private Boolean enabled;

    @JoinColumn(name = "min_level", referencedColumnName = "lid")
    @ManyToOne
    @Expose
    private UserLevel minLevel;

    @Transient
    @Expose
    private float rate;

    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    @Expose
    private User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostTags> postTagsList;

    @OneToMany(mappedBy = "post")
    private List<Thread> threadList;

    @OneToMany(mappedBy = "post")
    private List<ClientAnnounce> clientAnnounceList;

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

    public byte[] getBencode()
    {
        return bencode;
    }

    public void setBencode(byte[] bencode)
    {
        this.bencode = bencode;
    }

    public List<ClientAnnounce> getClientAnnounceList()
    {
        return clientAnnounceList;
    }

    public void setClientAnnounceList(List<ClientAnnounce> clientAnnounceList)
    {
        this.clientAnnounceList = clientAnnounceList;
    }

    public String getInfoHash()
    {
        return infoHash;
    }

    public void setInfoHash(String infoHash)
    {
        this.infoHash = infoHash;
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

    public UserLevel getMinLevel()
    {
        return minLevel;
    }

    public void setMinLevel(UserLevel minLevel)
    {
        this.minLevel = minLevel;
    }

    public User getAuthor()
    {
        return author;
    }

    public void setAuthor(User author)
    {
        this.author = author;
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

    @Override
    protected Class<Post> getRealClass()
    {
        return Post.class;
    }

    /**
     * If the given user could read this object.
     *
     * @param user
     *
     * @return
     */
    public boolean canRead(User user)
    {
        //------------This user is author or  reaches minimum level-------------
        if (this.getAuthor().equals(user) || user.getLevel().compareTo(this.getMinLevel()) >= 0)
        {
            return true;
        }
        List<Admin> admins = user.getAdminList();
        return admins.stream().anyMatch((admin) -> (admin.getLevel() == Admin.Level.INSPECTOR));
    }

    /**
     * If the given user could write/delete this object.
     *
     * @param user
     *
     * @return
     */
    public boolean canWrite(User user)
    {
        //------------This user is author-------------
        if (this.getAuthor().equals(user))
        {
            return true;
        }
        List<Admin> admins = user.getAdminList();
        //----------This user is admin with sufficient privilege-------------
        return admins.stream().anyMatch((admin) -> (admin.getLevel() == Admin.Level.ADMIN));
    }

}
