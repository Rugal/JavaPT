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
@Table(catalog = "postgres", schema = "jpt", name = "admin")
public class Admin
{

    private static final String sequence_name = "admin_aid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequence_name)
    @SequenceGenerator(name = sequence_name, sequenceName = SystemDefaultProperties.SCHEMA + sequence_name, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer aid;

    @Column
    private Long since;

    @Column
    private Level level;

    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    private User uid;

    @JoinColumn(name = "grantee", referencedColumnName = "uid")
    @ManyToOne
    private User grantee;

    public Admin()
    {
    }

    public Admin(Integer aid)
    {
        this.aid = aid;
    }

    public Integer getAid()
    {
        return aid;
    }

    public void setAid(Integer aid)
    {
        this.aid = aid;
    }

    public Long getSince()
    {
        return since;
    }

    public void setSince(Long since)
    {
        this.since = since;
    }

    public Level getLevel()
    {
        return level;
    }

    public void setLevel(Level level)
    {
        this.level = level;
    }

    public User getUid()
    {
        return uid;
    }

    public void setUid(User uid)
    {
        this.uid = uid;
    }

    public User getGrantee()
    {
        return grantee;
    }

    public void setGrantee(User grantee)
    {
        this.grantee = grantee;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (aid != null ? aid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Admin))
        {
            return false;
        }
        Admin other = (Admin) object;
        return !((this.aid == null && other.aid != null) || (this.aid != null && !this.aid.equals(other.aid)));
    }

    @Override
    public String toString()
    {
        return "ga.rugal.jpt.core.entity.Admin[ aid=" + aid + " ]";
    }

    public enum Level
    {

        UPLOADER, INSPECTOR, MONITOR, ADMIN, SUPER;
    }

}
