package ga.rugal.jpt.core.entity;

import com.google.gson.annotations.Expose;
import config.SystemDefaultProperties;
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
@Table(schema = "jpt", name = "client")
public class Client
{

    private static final String sequence_name = "client_cid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequence_name)
    @SequenceGenerator(name = sequence_name,
        sequenceName = SystemDefaultProperties.SCHEMA + sequence_name, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer cid;

    @Column(length = 50)
    @Expose
    private String name;

    @Expose
    @Column(length = 10)
    private String version;

    @Expose
    @Column(length = 10)
    private String cname;

    @Expose
    @Column
    private Boolean enabled;

    @OneToMany(mappedBy = "client")
    private List<ClientAnnounce> clientAnnouncesList;

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

    public List<ClientAnnounce> getClientAnnouncesList()
    {
        return clientAnnouncesList;
    }

    public void setClientAnnouncesList(List<ClientAnnounce> clientAnnouncesList)
    {
        this.clientAnnouncesList = clientAnnouncesList;
    }

    public String getCname()
    {
        return cname;
    }

    public void setCname(String cname)
    {
        this.cname = cname;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public Boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
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
        if (!(object instanceof Client))
        {
            return false;
        }
        Client other = (Client) object;
        return !((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid)));
    }

    @Override
    public String toString()
    {
        return "ga.rugal.jpt.core.entity.Client[ cid=" + cid + " ]";
    }

}
