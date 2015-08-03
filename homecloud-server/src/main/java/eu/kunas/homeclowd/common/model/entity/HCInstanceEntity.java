package eu.kunas.homeclowd.common.model.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ramazan on 15.05.15.
 */
@Entity
@Table(name = "HC_INSTANCE")
public class HCInstanceEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(name = "SEQ_HC_INSTANCE", sequenceName = "SEQ_HC_INSTANCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HC_INSTANCE")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SERVER")
    private String server;

    @Column(name = "PORT")
    private String port;

    @Column(name = "context")
    private String context;

    public HCInstanceEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
