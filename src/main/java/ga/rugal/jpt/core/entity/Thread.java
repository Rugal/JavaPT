package ga.rugal.jpt.core.entity;

import com.google.gson.annotations.Expose;
import config.SystemDefaultProperties;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Rugal Bernstein
 */
@Entity
@Table(schema = "jpt", name = "thread")
public class Thread extends BaseObject<Thread>
{

    private static final String sequence_name = "thread_tid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequence_name)
    @SequenceGenerator(name = sequence_name, sequenceName = SystemDefaultProperties.SCHEMA + sequence_name, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer tid;

    @Column(length = 2147483647)
    @Expose
    private String content;

    @Column(name = "post_time")
    @Expose
    private Long postTime;

    @Column(name = "rate")
    @Expose
    private Integer rate;

    @JoinColumn(name = "pid", referencedColumnName = "pid")
    @ManyToOne
    @Expose
    private Post post;

    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    @Expose
    private User replyer;

    public Thread()
    {
    }

    public Thread(Integer tid)
    {
        this.tid = tid;
    }

    public Integer getTid()
    {
        return tid;
    }

    public void setTid(Integer tid)
    {
        this.tid = tid;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Long getPostTime()
    {
        return postTime;
    }

    public void setPostTime(Long postTime)
    {
        this.postTime = postTime;
    }

    public Integer getRate()
    {
        return rate;
    }

    public void setRate(Integer rate)
    {
        this.rate = rate;
    }

    public Post getPost()
    {
        return post;
    }

    public void setPost(Post post)
    {
        this.post = post;
    }

    public User getReplyer()
    {
        return replyer;
    }

    public void setReplyer(User replyer)
    {
        this.replyer = replyer;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (tid != null ? tid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Thread))
        {
            return false;
        }
        Thread other = (Thread) object;
        return !((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid)));
    }

    @Override
    public String toString()
    {
        return "ga.rugal.jpt.core.entity.Thread[ tid=" + tid + " ]";
    }

    @Override
    protected Class<Thread> getRealClass()
    {
        return Thread.class;
    }

}
