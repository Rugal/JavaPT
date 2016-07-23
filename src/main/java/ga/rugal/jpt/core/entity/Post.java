package ga.rugal.jpt.core.entity;

import com.google.gson.annotations.Expose;
import config.SystemDefaultProperties;
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
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Rugal Bernstein
 */
@Entity
@Table(schema = "jpt", name = "post")
@Data
@EqualsAndHashCode(callSuper = false)
public class Post extends BaseObject<Post>
{

    private static final String SEQUENCE_NAME = "post_pid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME,
        sequenceName = SystemDefaultProperties.SCHEMA + SEQUENCE_NAME, allocationSize = 1)
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
    @Column(length = 50, name = "info_hash")
    private String infoHash;

    @Expose
    @Column(name = "post_time")
    private Long postTime;

    @Column(length = 2147483647)
    @Expose
    private byte[] bencode;

    @Column
    @Expose
    private Integer size;

    @Column
    @Expose
    private Boolean enabled;

    @JoinColumn(name = "min_level", referencedColumnName = "lid")
    @ManyToOne
    @Expose
    private UserLevel minLevel;

    @Transient
    @Expose
    private float rate;

    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    @Expose
    private User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostTags> postTagsList;

    @OneToMany(mappedBy = "post")
    private List<Thread> threadList;

    @OneToMany(mappedBy = "post")
    private List<ClientAnnounce> clientAnnounceList;

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
        if (this.getAuthor().equals(user) || user.getLevel().compareTo(this.getMinLevel()) >= 0)
        {
            return true;
        }
        List<Admin> admins = user.getAdminList();
        return admins.stream().anyMatch((admin) -> (admin.getLevel() == Admin.Level.INSPECTOR));
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
        return admins.stream().anyMatch((admin) -> (admin.getLevel() == Admin.Level.ADMIN));
    }

}
