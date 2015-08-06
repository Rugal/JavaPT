package ga.rugal.jpt.core.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Rugal Bernstein
 */
@Entity
@Table(catalog = "postgres", schema = "jpt", name = "level")
public class Level
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "level_lid_seq")
    @SequenceGenerator(name = "level_lid_seq", sequenceName = "jpt.level_lid_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer lid;

    @Column
    private Integer minimum;

    @Column(length = 50)
    private String name;

    public Level()
    {
    }

    public Level(Integer lid)
    {
        this.lid = lid;
    }

    public Integer getLid()
    {
        return lid;
    }

    public void setLid(Integer lid)
    {
        this.lid = lid;
    }

    public Integer getMinimum()
    {
        return minimum;
    }

    public void setMinimum(Integer minimum)
    {
        this.minimum = minimum;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (lid != null ? lid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Level))
        {
            return false;
        }
        Level other = (Level) object;
        return !((this.lid == null && other.lid != null) || (this.lid != null && !this.lid.equals(other.lid)));
    }

    @Override
    public String toString()
    {
        return "ga.rugal.jpt.core.entity.Level[ lid=" + lid + " ]";
    }

}
