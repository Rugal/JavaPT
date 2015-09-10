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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Rugal Bernstein
 */
@Entity
@Table(catalog = "postgres", schema = "jpt", name = "level")
public class UserLevel
{

    private static final String sequence_name = "level_lid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequence_name)
    @SequenceGenerator(name = sequence_name, sequenceName = SystemDefaultProperties.SCHEMA + sequence_name, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer lid;

    @Column
    @Expose
    private Integer minimum;

    @Column(length = 50)
    @Expose
    private String name;

    @Column
    @Expose
    private String icon;

    @Column(name = "min_upload_byte")
    @Expose
    private Long minUploadByte;

    @Column(name = "min_download_byte")
    @Expose
    private Long minDownloadByte;

    @OneToMany(mappedBy = "minLevel")
    private List<Post> postList;

    public UserLevel()
    {
    }

    public UserLevel(Integer lid)
    {
        this.lid = lid;
    }

    public Integer getLid()
    {
        return lid;
    }

    public void setLid(Integer lid)
    {
        this.lid = lid;
    }

    public Long getMinUploadByte()
    {
        return minUploadByte;
    }

    public void setMinUploadByte(Long minUploadByte)
    {
        this.minUploadByte = minUploadByte;
    }

    public Long getMinDownloadByte()
    {
        return minDownloadByte;
    }

    public void setMinDownloadByte(Long minDownloadByte)
    {
        this.minDownloadByte = minDownloadByte;
    }

    public Integer getMinimum()
    {
        return minimum;
    }

    public void setMinimum(Integer minimum)
    {
        this.minimum = minimum;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Post> getPostList()
    {
        return postList;
    }

    public void setPostList(List<Post> postList)
    {
        this.postList = postList;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (lid != null ? lid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof UserLevel))
        {
            return false;
        }
        UserLevel other = (UserLevel) object;
        return !((this.lid == null && other.lid != null) || (this.lid != null && !this.lid.equals(other.lid)));
    }

    @Override
    public String toString()
    {
        return "ga.rugal.jpt.core.entity.Level[ lid=" + lid + " ]";
    }

}
