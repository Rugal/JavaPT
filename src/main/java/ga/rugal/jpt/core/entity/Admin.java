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
import lombok.EqualsAndHashCode;

/**
 *
 * @author Rugal Bernstein
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(schema = "jpt", name = "admin")
public class Admin extends BaseObject<Admin>
{

    private static final String SEQUENCE_NAME = "admin_aid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME,
        sequenceName = SystemDefaultProperties.SCHEMA + SEQUENCE_NAME, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer aid;

    @Expose
    @Column
    private Long since;

    @Expose
    @Column
    private Level level;

    @Expose
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    private User user;

    @Expose
    @JoinColumn(name = "granter", referencedColumnName = "uid")
    @ManyToOne
    private User granter;

    @Override
    protected Class<Admin> getRealClass()
    {
        return Admin.class;
    }

    public enum Level
    {

        //User who can create new post to candidate list and add torront for it
        UPLOADER,
        //User who can inspect new post and publish them to the tracker
        INSPECTOR,
        //User who can access and modify any post
        ADMIN,
        //User who can shutdown the whole site
        SUPER;
    }

}
