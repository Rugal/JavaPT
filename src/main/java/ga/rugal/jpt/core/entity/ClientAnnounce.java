package ga.rugal.jpt.core.entity;

import com.google.gson.annotations.Expose;
import ga.rugal.jpt.common.SystemDefaultProperties;
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

@Entity
@Table(name = "client_announce", schema = "jpt")
public class ClientAnnounce
{

    private static final String sequence_name = "client_announce_caid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequence_name)
    @SequenceGenerator(name = sequence_name, sequenceName = SystemDefaultProperties.SCHEMA + sequence_name, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Long caid;

    @Expose
    @Column(name = "announce_time")
    private Long announceTime;

    @Expose
    @Column(name = "download_byte")
    private Long downloadByte = 0l;

    @Expose
    @Column(name = "upload_byte")
    private Long uploadByte = 0l;

    @Expose
    @Column(name = "left_byte")
    private Long leftByte = 0l;

    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    @Expose
    private User uid;

    @Expose
    @JoinColumn(name = "cid", referencedColumnName = "cid")
    @ManyToOne
    private Client cid;

    @Expose
    @JoinColumn(name = "pid", referencedColumnName = "pid", nullable = false)
    @ManyToOne
    private Post pid;

    public ClientAnnounce()
    {
    }

    public ClientAnnounce(Long caid)
    {
        this.caid = caid;
    }

    public Long getCaid()
    {
        return caid;
    }

    public void setCaid(Long caid)
    {
        this.caid = caid;
    }

    public Long getAnnounceTime()
    {
        return announceTime;
    }

    public void setAnnounceTime(Long announceTime)
    {
        this.announceTime = announceTime;
    }

    public User getUid()
    {
        return uid;
    }

    public void setUid(User uid)
    {
        this.uid = uid;
    }

    public Client getCid()
    {
        return cid;
    }

    public void setCid(Client cid)
    {
        this.cid = cid;
    }

    public Long getDownloadByte()
    {
        return downloadByte;
    }

    public void setDownloadByte(Long downloadByte)
    {
        this.downloadByte = downloadByte;
    }

    public Long getUploadByte()
    {
        return uploadByte;
    }

    public void setUploadByte(Long uploadByte)
    {
        this.uploadByte = uploadByte;
    }

    public Long getLeftByte()
    {
        return leftByte;
    }

    public void setLeftByte(Long leftByte)
    {
        this.leftByte = leftByte;
    }

    public Post getPid()
    {
        return pid;
    }

    public void setPid(Post pid)
    {
        this.pid = pid;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (caid != null ? caid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof ClientAnnounce))
        {
            return false;
        }
        ClientAnnounce other = (ClientAnnounce) object;
        if ((this.caid == null && other.caid != null) || (this.caid != null && !this.caid.equals(other.caid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "ga.rugal.jpt.core.entity.ClientAnnounce[ caid=" + caid + " ]";
    }

}
