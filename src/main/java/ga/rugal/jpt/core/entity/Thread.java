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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Rugal Bernstein
 */
@Entity
@Table(schema = "jpt", name = "thread")
@Data
@EqualsAndHashCode(callSuper = false)
public class Thread extends BaseObject<Thread>
{

    private static final String SEQUENCE_NAME = "thread_tid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME,
        sequenceName = SystemDefaultProperties.SCHEMA + SEQUENCE_NAME, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer tid;

    @Column(length = 2147483647)
    @Expose
    private String content;

    @Column(name = "post_time")
    @Expose
    private Long postTime;

    @Column(name = "rate")
    @Expose
    private Integer rate;

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
        return admins.stream().anyMatch((admin) -> (admin.getLevel() == Admin.Level.ADMIN));
    }
}
