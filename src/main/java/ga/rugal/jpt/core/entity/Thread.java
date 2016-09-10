package ga.rugal.jpt.core.entity;

import com.google.gson.annotations.Expose;
import static config.SystemDefaultProperties.SCHEMA;
import java.util.List;
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
@Table(schema = SCHEMA, name = "thread")
@Data
public class Thread extends BaseObject<Thread>
{

    private static final String SEQUENCE_NAME = "thread_tid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SCHEMA + "." + SEQUENCE_NAME, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer tid;

    @Column(length = 2147483647)
    @Expose
    private String content;

    @Column(name = "create_time")
    @Expose
    private Long createTime;

    @Column(name = "judgement")
    @Expose
    private Boolean judgement;

    @JoinColumn(name = "pid", referencedColumnName = "pid")
    @ManyToOne
    @Expose
    private Post post;

    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    @Expose
    private User replyer;

    @Override
    protected Class<Thread> getRealClass()
    {
        return Thread.class;
    }

    /**
     * If the given user could read this object.
     *
     * @param user
     *
     * @return
     */
    public boolean canRead(User user)
    {
        return true;
    }

    /**
     * If the given user could write/delete this object.
     *
     * @param user
     *
     * @return
     */
    public boolean canWrite(User user)
    {
        //------------This user is author-------------
        if (this.getReplyer().equals(user))
        {
            return true;
        }
        List<Admin> admins = user.getAdminList();
        //----------This user is admin with sufficient privilege-------------
        return admins.stream().anyMatch((admin) -> (admin.getRole() == Admin.Role.ADMIN));
    }

    @Override
    public String toString()
    {
        return String.format("%s[ tid=%d ]", this.getClass().getName(), this.tid);
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Thread))
        {
            return false;
        }
        Thread other = (Thread) object;
        return !((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid)));
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        return 37 * hash + (tid != null ? tid.hashCode() : 0);
    }
}
