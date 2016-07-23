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
@Table(schema = "jpt", name = "client")
@Data
@EqualsAndHashCode(callSuper = false)
public class Client
{

    private static final String SEQUENCE_NAME = "client_cid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME,
        sequenceName = SystemDefaultProperties.SCHEMA + SEQUENCE_NAME, allocationSize = 1)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer cid;

    @Column(length = 50)
    @Expose
    private String name;

    @Expose
    @Column(length = 10)
    private String version;

    @Expose
    @Column(length = 10)
    private String cname;

    @Expose
    @Column
    private Boolean enabled;

    @OneToMany(mappedBy = "client")
    private List<ClientAnnounce> clientAnnouncesList;
}
