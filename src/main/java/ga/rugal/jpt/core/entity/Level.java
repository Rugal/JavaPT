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
@Table(schema = SCHEMA, name = "level")
@Data
public class Level implements Comparable<Level>
{

    private static final String SEQUENCE_NAME = "level_lid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SCHEMA + "." + SEQUENCE_NAME, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer lid;

    @Column(length = 50)
    @Expose
    private String name;

    @Column
    @Expose
    private String icon;

    @Column(name = "upload")
    @Expose
    private Long upload;

    @Column(name = "download")
    @Expose
    private Long download;

    @Override
    public int compareTo(Level o)
    {
        return this.lid - o.lid;
    }

    @Override
    public String toString()
    {
        return String.format("%s[ lid=%d ]", this.getClass().getName(), this.lid);
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Level))
        {
            return false;
        }
        Level other = (Level) object;
        return !((this.lid == null && other.lid != null) || (this.lid != null && !this.lid.equals(other.lid)));
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        return 37 * hash + (lid != null ? lid.hashCode() : 0);
    }
}
