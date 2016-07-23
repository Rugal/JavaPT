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
@Table(name = "post_tags", schema = "jpt")
@Data
@EqualsAndHashCode(callSuper = false)
public class PostTags
{

    private static final String SEQUENCE_NAME = "post_tags_ptid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME,
        sequenceName = SystemDefaultProperties.SCHEMA + SEQUENCE_NAME, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer ptid;

    @Expose
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    @ManyToOne
    private Post post;

    @Expose
    @JoinColumn(name = "tid", referencedColumnName = "tid")
    @ManyToOne
    private Tag tag;
}
