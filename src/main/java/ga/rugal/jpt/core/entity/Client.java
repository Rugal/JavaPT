package ga.rugal.jpt.core.entity;

import com.google.gson.annotations.Expose;
import static config.SystemDefaultProperties.SCHEMA;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Rugal Bernstein
 */
@Entity
@Table(schema = SCHEMA, name = "client")
@Data
public class Client
{

    private static final String SEQUENCE_NAME = "client_cid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SCHEMA + "." + SEQUENCE_NAME, allocationSize = 1)
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
    private Boolean enable;

    @Override
    public String toString()
    {
        return String.format("%s[ cid=%d ]", this.getClass().getName(), this.cid);
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
    public int hashCode()
    {
        int hash = 7;
        return 37 * hash + (cid != null ? cid.hashCode() : 0);
    }
}
