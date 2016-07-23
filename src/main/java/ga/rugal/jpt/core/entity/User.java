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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Rugal Bernstein
 */
@Entity
@Table(schema = "jpt", name = "user")
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BaseObject<User>
{

    private static final String SEQUENCE_NAME = "user_uid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME,
        sequenceName = SystemDefaultProperties.SCHEMA + SEQUENCE_NAME, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer uid;

    @Column(length = 100)
    @Expose
    private String password;

    @Expose
    @Column(length = 100)
    private String username;

    @Expose
    @Column(length = 100)
    private String email;

    @Expose
    @Column(name = "upload_byte")
    private Long uploadByte = 0l;

    @Expose
    @Column(name = "download_byte")
    private Long downloadByte = 0l;

    @Expose
    @Column
    private Integer credit = 0;

    @Expose
    @Column(name = "register_time")
    private Long registerTime;

    @Expose
    @Transient
    private UserLevel level;

    @Expose
    @Column
    private Status status;

    @JoinColumn(name = "referee", referencedColumnName = "uid")
    @ManyToOne
    private User referee;

    @OneToMany(mappedBy = "author")
    private List<Post> postList;

    @OneToMany(mappedBy = "user")
    private List<ClientAnnounce> clientAnnouncesList;

    @OneToMany(mappedBy = "user")
    @Fetch(FetchMode.SELECT)
    private List<Admin> adminList;

    @OneToMany(mappedBy = "granter")
    private List<Admin> granters;

    @OneToMany(mappedBy = "uid")
    private List<SigninLog> signinLogList;

    @OneToMany(mappedBy = "replyer")
    private List<Thread> threadList;

    @OneToMany(mappedBy = "referee")
    private List<User> userList;

    @Override
    protected Class<User> getRealClass()
    {
        return User.class;
    }

    public enum Status
    {

        VALID, BAN, DELETING
    }
}
