package ga.rugal.jpt.core.entity;

import com.google.gson.annotations.Expose;
import static config.SystemDefaultProperties.SCHEMA;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Rugal Bernstein
 */
@Entity
@Table(schema = SCHEMA, name = "post")
@Data
public class Post extends BaseObject<Post>
{

    private static final String SEQUENCE_NAME = "post_pid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SCHEMA + "." + SEQUENCE_NAME, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer pid;

    @Expose
    @Column(length = 50)
    private String title;

    @Expose
    @Column(length = 2147483647)
    private String content;

    @Expose
    @Column(length = 50, name = "hash")
    private String hash;

    @Expose
    @Column(name = "create_time")
    private Long createTime;

    @Column(length = 2147483647)
    @Expose
    private byte[] bencode;

    @Column
    @Expose
    private Integer size;

    @Column
    @Expose
    private Boolean enable;

    @JoinColumn(name = "lid", referencedColumnName = "lid")
    @ManyToOne
    @Expose
    private Level level;

    @Column
    @Expose
    private Integer rate;

    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    @Expose
    private User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Tagging> postTagsList;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Thread> threadList;

    @Override
    protected Class<Post> getRealClass()
    {
        return Post.class;
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
        //------------This user is author or  reaches minimum level-------------
        if (this.getAuthor().equals(user) || user.getLevel().compareTo(this.getLevel()) >= 0)
        {
            return true;
        }
        List<Admin> admins = user.getAdminList();
        return admins.stream().anyMatch((admin) -> (admin.getRole() == Admin.Role.INSPECTOR));
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
        if (this.getAuthor().equals(user))
        {
            return true;
        }
        List<Admin> admins = user.getAdminList();
        //----------This user is admin with sufficient privilege-------------
        return admins.stream().anyMatch((admin) -> (admin.getRole() == Admin.Role.ADMINISTRATOR));
    }

    @Override
    public String toString()
    {
        return String.format("%s[ pid=%d ]", this.getClass().getName(), this.pid);
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Post))
        {
            return false;
        }
        Post other = (Post) object;
        return !((this.pid == null && other.pid != null) || (this.pid != null && !this.pid.equals(other.pid)));
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        return 37 * hash + (pid != null ? pid.hashCode() : 0);
    }
}
