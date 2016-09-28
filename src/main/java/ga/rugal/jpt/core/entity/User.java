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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Rugal Bernstein
 */
@Entity
@Table(schema = SCHEMA, name = "user")
@Data
public class User extends BaseObject<User>
{

    private static final String SEQUENCE_NAME = "user_uid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SCHEMA + "." + SEQUENCE_NAME, allocationSize = 1)
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
    @Column(name = "upload")
    private Long upload;

    @Expose
    @Column(name = "download")
    private Long download;

    @Expose
    @Column
    private Integer credit;

    @Expose
    @Column(name = "register_time")
    private Long registerTime;

    @Expose
    @Transient
    private Level level;

    @Expose
    @Column
    private Status status;

    @OneToMany(mappedBy = "author")
    private List<Post> postList;

    @OneToMany(mappedBy = "user")
    private List<Announce> announcesList;

    @OneToMany(mappedBy = "invitor")
    private List<Invitation> invitationCodeList;

    @OneToOne(mappedBy = "invitee")
    private Invitation invitorRelation;

    @OneToMany(mappedBy = "user")
    @Fetch(FetchMode.SELECT)
    private List<Admin> adminList;

    @OneToMany(mappedBy = "granter")
    private List<Admin> granters;

    @OneToMany(mappedBy = "replyer")
    private List<Thread> threadList;

    @Override
    protected Class<User> getRealClass()
    {
        return User.class;
    }

    /**
     * Update user credit with the given DIFF.
     *
     * @param diff This number could be both positive and negative
     *
     * @return The new credit
     */
    public Integer updateCredit(Integer diff)
    {
        return this.credit + diff;
    }

    public enum Status
    {

        VALID, BAN, DELETE, INITIAL
    }

    @Override
    public String toString()
    {
        return String.format("%s[ uid=%d ]", this.getClass().getName(), this.uid);
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof User))
        {
            return false;
        }
        User other = (User) object;
        return !((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid)));
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        return 37 * hash + (uid != null ? uid.hashCode() : 0);
    }
}
