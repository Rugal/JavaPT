package ga.rugal.jpt.core.entity;

import com.google.gson.annotations.Expose;
import ga.rugal.jpt.common.SystemDefaultProperties;
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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Rugal Bernstein
 */
@Entity
@Table(schema = "jpt", name = "user")
public class User
{

    private static final String sequence_name = "user_uid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequence_name)
    @SequenceGenerator(name = sequence_name, sequenceName = SystemDefaultProperties.SCHEMA + sequence_name, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer uid;

    @Expose
    @Column(length = 100)
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

    @OneToMany(mappedBy = "uid")
    private List<Post> postList;

    @OneToMany(mappedBy = "caid")
    private List<ClientAnnounce> clientAnnouncesList;

    @OneToMany(mappedBy = "uid")
    @Fetch(FetchMode.SELECT)
    private List<Admin> adminList;

    @OneToMany(mappedBy = "grantee")
    private List<Admin> grantees;

    @OneToMany(mappedBy = "uid")
    private List<SigninLog> signinLogList;

    @OneToMany(mappedBy = "uid")
    private List<Thread> threadList;

    @OneToMany(mappedBy = "referee")
    private List<User> userList;

    public User()
    {
    }

    public User(Integer uid)
    {
        this.uid = uid;
    }

    public Integer getUid()
    {
        return uid;
    }

    public void setUid(Integer uid)
    {
        this.uid = uid;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public List<Admin> getGrantees()
    {
        return grantees;
    }

    public void setGrantees(List<Admin> grantees)
    {
        this.grantees = grantees;
    }

    public List<ClientAnnounce> getClientAnnouncesList()
    {
        return clientAnnouncesList;
    }

    public void setClientAnnouncesList(List<ClientAnnounce> clientAnnouncesList)
    {
        this.clientAnnouncesList = clientAnnouncesList;
    }

    public UserLevel getLevel()
    {
        return level;
    }

    public void setLevel(UserLevel level)
    {
        this.level = level;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Long getUploadByte()
    {
        return uploadByte;
    }

    public void setUploadByte(Long uploadByte)
    {
        this.uploadByte = uploadByte;
    }

    public Long getDownloadByte()
    {
        return downloadByte;
    }

    public void setDownloadByte(Long downloadByte)
    {
        this.downloadByte = downloadByte;
    }

    public Integer getCredit()
    {
        return credit;
    }

    public void setCredit(Integer credit)
    {
        this.credit = credit;
    }

    public Long getRegisterTime()
    {
        return registerTime;
    }

    public void setRegisterTime(Long registerTime)
    {
        this.registerTime = registerTime;
    }

    public List<Post> getPostList()
    {
        return postList;
    }

    public void setPostList(List<Post> postList)
    {
        this.postList = postList;
    }

    public List<Admin> getAdminList()
    {
        return adminList;
    }

    public void setAdminList(List<Admin> adminList)
    {
        this.adminList = adminList;
    }

    public List<SigninLog> getSigninLogList()
    {
        return signinLogList;
    }

    public void setSigninLogList(List<SigninLog> signinLogList)
    {
        this.signinLogList = signinLogList;
    }

    public List<Thread> getThreadList()
    {
        return threadList;
    }

    public void setThreadList(List<Thread> threadList)
    {
        this.threadList = threadList;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public List<User> getUserList()
    {
        return userList;
    }

    public void setUserList(List<User> userList)
    {
        this.userList = userList;
    }

    public User getReferee()
    {
        return referee;
    }

    public void setReferee(User referee)
    {
        this.referee = referee;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (uid != null ? uid.hashCode() : 0);
        return hash;
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
    public String toString()
    {
        return "ga.rugal.jpt.core.entity.User[ uid=" + uid + " ]";
    }

    public enum Status
    {

        VALID, BAN, DELETING
    }

}
