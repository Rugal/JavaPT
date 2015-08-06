package ga.rugal.jpt.core.entity;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Rugal Bernstein
 */
@Entity
@Table(name = "admin_level", catalog = "postgres", schema = "jpt")
public class AdminLevel
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_level_alid_seq")
    @SequenceGenerator(name = "admin_level_alid_seq", sequenceName = "jpt.admin_level_alid_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer alid;

    @Column(length = 50)
    private String name;

    @OneToMany(mappedBy = "alid")
    private List<Admin> adminList;

    public AdminLevel()
    {
    }

    public AdminLevel(Integer alid)
    {
        this.alid = alid;
    }

    public Integer getAlid()
    {
        return alid;
    }

    public void setAlid(Integer alid)
    {
        this.alid = alid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Admin> getAdminList()
    {
        return adminList;
    }

    public void setAdminList(List<Admin> adminList)
    {
        this.adminList = adminList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (alid != null ? alid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdminLevel))
        {
            return false;
        }
        AdminLevel other = (AdminLevel) object;
        if ((this.alid == null && other.alid != null) || (this.alid != null && !this.alid.equals(other.alid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "ga.rugal.jpt.core.entity.AdminLevel[ alid=" + alid + " ]";
    }

}
