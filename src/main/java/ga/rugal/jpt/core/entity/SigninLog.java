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

/**
 *
 * @author Rugal Bernstein
 */
@Entity
@Table(name = "signin_log", schema = "jpt")
public class SigninLog extends BaseObject<SigninLog>
{

    private static final String sequence_name = "signin_log_slid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequence_name)
    @SequenceGenerator(name = sequence_name, sequenceName = SystemDefaultProperties.SCHEMA + sequence_name, allocationSize = 1)
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

    public SigninLog()
    {
    }

    public SigninLog(Integer slid)
    {
        this.slid = slid;
    }

    public Integer getSlid()
    {
        return slid;
    }

    public void setSlid(Integer slid)
    {
        this.slid = slid;
    }

    public Long getSigninTime()
    {
        return signinTime;
    }

    public void setSigninTime(Long signinTime)
    {
        this.signinTime = signinTime;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public User getUid()
    {
        return uid;
    }

    public void setUid(User uid)
    {
        this.uid = uid;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (slid != null ? slid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof SigninLog))
        {
            return false;
        }
        SigninLog other = (SigninLog) object;
        return !((this.slid == null && other.slid != null) || (this.slid != null && !this.slid.equals(other.slid)));
    }

    @Override
    public String toString()
    {
        return "ga.rugal.jpt.core.entity.SigninLog[ slid=" + slid + " ]";
    }

    @Override
    protected Class<SigninLog> getRealClass()
    {
        return SigninLog.class;
    }

}
