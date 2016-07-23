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
@Table(name = "signin_log", schema = "jpt")
@Data
@EqualsAndHashCode(callSuper = false)
public class SigninLog extends BaseObject<SigninLog>
{

    private static final String SEQUENCE_NAME = "signin_log_slid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME,
        sequenceName = SystemDefaultProperties.SCHEMA + SEQUENCE_NAME, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer slid;

    @Expose
    @Column(name = "signin_time")
    private Long signinTime;

    @Expose
    @Column(length = 30)
    private String ip;

    @Expose
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    private User uid;

    @Override
    protected Class<SigninLog> getRealClass()
    {
        return SigninLog.class;
    }

}
