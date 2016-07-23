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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Rugal Bernstein
 */
@Entity
@Table(schema = "jpt", name = "level")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserLevel implements Comparable<UserLevel>
{

    private static final String SEQUENCE_NAME = "level_lid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME,
        sequenceName = SystemDefaultProperties.SCHEMA + SEQUENCE_NAME, allocationSize = 1)
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

    @Override
    public int compareTo(UserLevel o)
    {
        return this.lid - o.lid;
    }

}
