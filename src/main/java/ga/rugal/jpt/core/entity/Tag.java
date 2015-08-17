package ga.rugal.jpt.core.entity;

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
@Table(catalog = "postgres", schema = "jpt", name = "tag")
public class Tag
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_tid_seq")
    @SequenceGenerator(name = "tag_tid_seq", sequenceName = "jpt.tag_tid_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer tid;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String icon;

    @OneToMany(mappedBy = "tid")
    private transient List<PostTags> postTagsList;

    public Tag()
    {
    }

    public Tag(Integer tid)
    {
        this.tid = tid;
    }

    public Integer getTid()
    {
        return tid;
    }

    public void setTid(Integer tid)
    {
        this.tid = tid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public List<PostTags> getPostTagsList()
    {
        return postTagsList;
    }

    public void setPostTagsList(List<PostTags> postTagsList)
    {
        this.postTagsList = postTagsList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (tid != null ? tid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tag))
        {
            return false;
        }
        Tag other = (Tag) object;
        return !((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid)));
    }

    @Override
    public String toString()
    {
        return "ga.rugal.jpt.core.entity.Tag[ tid=" + tid + " ]";
    }

}
