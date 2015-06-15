package eu.kunas.homeclowd.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ramazan on 15.05.15.
 */
@Entity
@Table(name = "HC_CONFIG")
public class HCConfigEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(name = "SEQ_HC_CONFIG", sequenceName = "SEQ_HC_CONFIG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HC_CONFIG")
    private Long id;

    @Column(name = "KEY")
    private String key;

    @Column(name = "VALUE")
    private String value;

    public HCConfigEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
