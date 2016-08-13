package ga.rugal.jpt.core.entity;

import com.google.gson.annotations.Expose;
import config.SystemDefaultProperties;
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

@Entity
@Table(name = "announce", schema = "jpt")
@Data
public class Announce
{

    private static final String SEQUENCE_NAME = "announce_aid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME,
        sequenceName = SystemDefaultProperties.SCHEMA + SEQUENCE_NAME, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Long aid;

    @Expose
    @Column(name = "announce_time")
    private Long announceTime;

    @Expose
    @Column(name = "download")
    private Long download = 0l;

    @Expose
    @Column(name = "upload")
    private Long upload = 0l;

    @Expose
    @Column(name = "_left")
    private Long left = 0l;

    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    @Expose
    private User user;

    @Expose
    @JoinColumn(name = "cid", referencedColumnName = "cid")
    @ManyToOne
    private Client client;

    @Expose
    @JoinColumn(name = "pid", referencedColumnName = "pid", nullable = false)
    @ManyToOne
    private Post post;

    @Override
    public String toString()
    {
        return String.format("%s[ aid=%d ]", this.getClass().getName(), this.aid);
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Announce))
        {
            return false;
        }
        Announce other = (Announce) object;
        return !((this.aid == null && other.aid != null) || (this.aid != null && !this.aid.equals(other.aid)));
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        return 37 * hash + (aid != null ? aid.hashCode() : 0);
    }
}
