package ga.rugal.jpt.core.entity;

import com.google.gson.annotations.Expose;
import static config.SystemDefaultProperties.SCHEMA;
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
import lombok.Data;

/**
 *
 * @author Rugal Bernstein
 */
@Entity
@Table(schema = SCHEMA, name = "post_tag")
@Data
public class Tagging
{

    private static final String SEQUENCE_NAME = "post_tags_ptid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SCHEMA + "." + SEQUENCE_NAME, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer ptid;

    @Expose
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    @ManyToOne
    private Post post;

    @Expose
    @JoinColumn(name = "tid", referencedColumnName = "tid")
    @ManyToOne
    private Tag tag;

    @Override
    public String toString()
    {
        return String.format("%s[ ptid=%d ]", this.getClass().getName(), this.ptid);
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Tagging))
        {
            return false;
        }
        Tagging other = (Tagging) object;
        return !((this.ptid == null && other.ptid != null) || (this.ptid != null && !this.ptid.equals(other.ptid)));
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        return 37 * hash + (ptid != null ? ptid.hashCode() : 0);
    }
}
