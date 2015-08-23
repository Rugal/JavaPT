package ga.rugal.jpt.core.entity;

import ga.rugal.jpt.common.SystemDefaultProperties;
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
@Table(name = "post_tags", catalog = "postgres", schema = "jpt")
public class PostTags
{

    private static final String sequence_name = "post_tags_ptid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequence_name)
    @SequenceGenerator(name = sequence_name, sequenceName = SystemDefaultProperties.SCHEMA + sequence_name, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer ptid;

    @JoinColumn(name = "pid", referencedColumnName = "pid")
    @ManyToOne
    private Post pid;

    @JoinColumn(name = "tid", referencedColumnName = "tid")
    @ManyToOne
    private Tag tid;

    public PostTags()
    {
    }

    public PostTags(Integer ptid)
    {
        this.ptid = ptid;
    }

    public Integer getPtid()
    {
        return ptid;
    }

    public void setPtid(Integer ptid)
    {
        this.ptid = ptid;
    }

    public Post getPid()
    {
        return pid;
    }

    public void setPid(Post pid)
    {
        this.pid = pid;
    }

    public Tag getTid()
    {
        return tid;
    }

    public void setTid(Tag tid)
    {
        this.tid = tid;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (ptid != null ? ptid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PostTags))
        {
            return false;
        }
        PostTags other = (PostTags) object;
        return !((this.ptid == null && other.ptid != null) || (this.ptid != null && !this.ptid.equals(other.ptid)));
    }

    @Override
    public String toString()
    {
        return "ga.rugal.jpt.core.entity.PostTags[ ptid=" + ptid + " ]";
    }

}
