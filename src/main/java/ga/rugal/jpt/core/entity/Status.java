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
@Table(catalog = "postgres", schema = "jpt", name = "status")
public class Status
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "status_sid_seq")
    @SequenceGenerator(name = "status_sid_seq", sequenceName = "jpt.status_sid_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer sid;

    @Column(length = 50)
    private String name;

    @OneToMany(mappedBy = "sid")
    private List<User> userList;

    public Status()
    {
    }

    public Status(Integer sid)
    {
        this.sid = sid;
    }

    public Integer getSid()
    {
        return sid;
    }

    public void setSid(Integer sid)
    {
        this.sid = sid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
        hash += (sid != null ? sid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Status))
        {
            return false;
        }
        Status other = (Status) object;
        if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "ga.rugal.jpt.core.entity.Status[ sid=" + sid + " ]";
    }

}
