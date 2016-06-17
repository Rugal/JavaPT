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
@Table(schema = "jpt", name = "invitation")
public class Invitation
{

    private static final String sequence_name = "invitation_iid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequence_name)
    @SequenceGenerator(name = sequence_name,
        sequenceName = SystemDefaultProperties.SCHEMA + sequence_name, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer iid;

    @Expose
    @Column(name = "issue_time")
    private Long issueTime;

    @Expose
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    private User user;

    public Invitation()
    {
    }

    public Integer getIid()
    {
        return iid;
    }

    public void setIid(Integer iid)
    {
        this.iid = iid;
    }

    public Long getIssueTime()
    {
        return issueTime;
    }

    public void setIssueTime(Long issueTime)
    {
        this.issueTime = issueTime;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (iid != null ? iid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Invitation))
        {
            return false;
        }
        Invitation other = (Invitation) object;
        return !((this.iid == null && other.iid != null) || (this.iid != null && !this.iid.equals(other.iid)));
    }

    @Override
    public String toString()
    {
        return "ga.rugal.jpt.core.entity.Post[ pid=" + iid + " ]";
    }
}
