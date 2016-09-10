package ga.rugal.jpt.core.entity;

import com.google.gson.annotations.Expose;
import static config.SystemDefaultProperties.SCHEMA;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Rugal Bernstein
 */
@Entity
@Data
@Table(schema = SCHEMA, name = "invitation")
public class Invitation
{

    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private String id;

    @Expose
    @JoinColumn(name = "invitor", referencedColumnName = "uid")
    @ManyToOne
    private User invitor;

    @Expose
    @JoinColumn(name = "invitee", referencedColumnName = "uid")
    @OneToOne
    private User invitee;

    @Override
    public String toString()
    {
        return String.format("%s[ id=%d ]", this.getClass().getName(), this.id);
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Invitation))
        {
            return false;
        }
        Invitation other = (Invitation) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public int hashCode()
    {
        int hash = 11;
        return 41 * hash + (id != null ? id.hashCode() : 0);
    }
}
