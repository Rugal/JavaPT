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

@Entity
@Table(name = "client_announce", schema = "jpt")
@Data
@EqualsAndHashCode(callSuper = false)
public class ClientAnnounce
{

    private static final String SEQUENCE_NAME = "client_announce_caid_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME,
        sequenceName = SystemDefaultProperties.SCHEMA + SEQUENCE_NAME, allocationSize = 1)
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
    private User user;

    @Expose
    @JoinColumn(name = "cid", referencedColumnName = "cid")
    @ManyToOne
    private Client client;

    @Expose
    @JoinColumn(name = "pid", referencedColumnName = "pid", nullable = false)
    @ManyToOne
    private Post post;
}
