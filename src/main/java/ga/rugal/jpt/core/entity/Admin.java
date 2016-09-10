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
@Data
@Table(schema = SCHEMA, name = "admin")
public class Admin extends BaseObject<Admin>
{

    private static final String SEQUENCE_NAME = "admin_aid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SCHEMA + "." + SEQUENCE_NAME, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer aid;

    @Expose
    @Column
    private Long since;

    @Expose
    @Column
    private Role role;

    @Expose
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    private User user;

    @Expose
    @JoinColumn(name = "granter", referencedColumnName = "uid")
    @ManyToOne
    private User granter;

    @Override
    public String toString()
    {
        return String.format("%s[ aid=%d ]", this.getClass().getName(), this.aid);
    }

    @Override
    protected Class<Admin> getRealClass()
    {
        return Admin.class;
    }

    public enum Role
    {

        //User who can create new post to candidate list
        UPLOADER,
        //User who can inspect new post and publish them to the tracker
        INSPECTOR,
        //User who can access and modify any post
        ADMIN,
        //User who can shutdown the whole site
        SUPER;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Admin))
        {
            return false;
        }
        Admin other = (Admin) object;
        return !((this.aid == null && other.aid != null) || (this.aid != null && !this.aid.equals(other.aid)));
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        return 37 * hash + (aid != null ? aid.hashCode() : 0);
    }
}
