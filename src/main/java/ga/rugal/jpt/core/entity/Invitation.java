package ga.rugal.jpt.core.entity;

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
@Table(catalog = "postgres", schema = "jpt", name = "invitation")
public class Invitation
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invitation_iid_seq")
    @SequenceGenerator(name = "invitation_iid_seq", sequenceName = "jpt.invitation_iid_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer iid;

    @Column(name = "issue_time")
    private Long issueTime;

    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    private User uid;

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

    public User getUid()
    {
        return uid;
    }

    public void setUid(User uid)
    {
        this.uid = uid;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invitation))
        {
            return false;
        }
        Invitation other = (Invitation) object;
        if ((this.iid == null && other.iid != null) || (this.iid != null && !this.iid.equals(other.iid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "ga.rugal.jpt.core.entity.Post[ pid=" + iid + " ]";
    }
}
