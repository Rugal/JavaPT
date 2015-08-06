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
@Table(catalog = "postgres", schema = "jpt", name = "client")
public class Client
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_cid_seq")
    @SequenceGenerator(name = "client_cid_seq", sequenceName = "jpt.client_cid_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer cid;

    @Column(length = 50)
    private String name;

    @Column(length = 10)
    private String version;

    @Column
    private Boolean enabled;

    @OneToMany(mappedBy = "cid")
    private List<User> userList;

    public Client()
    {
    }

    public Client(Integer cid)
    {
        this.cid = cid;
    }

    public Integer getCid()
    {
        return cid;
    }

    public void setCid(Integer cid)
    {
        this.cid = cid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public Boolean getEnabled()
    {
        return enabled;
    }

    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

    public List<User> getUserList()
    {
        return userList;
    }

    public void setUserList(List<User> userList)
    {
        this.userList = userList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client))
        {
            return false;
        }
        Client other = (Client) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "ga.rugal.jpt.core.entity.Client[ cid=" + cid + " ]";
    }

}
