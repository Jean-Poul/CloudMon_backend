
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "services")
@NamedQuery(name = "Service.deleteAllRows", query = "DELETE from Service")
@NamedQuery(name = "Service.getAllRows", query = "SELECT s from Service s")
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namespace;
    private String name;
    private String type;
    private String clusterip;
    private String externalip;
    private String port;
    private String age;
    private String selector;

    // Constructors
    public Service() {

    }

    public Service(String namespace, String name, String type, String clusterip, String externalip, String port, String age, String selector) {
        this.namespace = namespace;
        this.name = name;
        this.type = type;
        this.clusterip = clusterip;
        this.externalip = externalip;
        this.port = port;
        this.age = age;
        this.selector = selector;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClusterip() {
        return clusterip;
    }

    public void setClusterip(String clusterip) {
        this.clusterip = clusterip;
    }

    public String getExternalip() {
        return externalip;
    }

    public void setExternalip(String externalip) {
        this.externalip = externalip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    // For test purpose
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.namespace);
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.type);
        hash = 71 * hash + Objects.hashCode(this.clusterip);
        hash = 71 * hash + Objects.hashCode(this.externalip);
        hash = 71 * hash + Objects.hashCode(this.port);
        hash = 71 * hash + Objects.hashCode(this.age);
        hash = 71 * hash + Objects.hashCode(this.selector);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Service other = (Service) obj;
        if (!Objects.equals(this.namespace, other.namespace)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.clusterip, other.clusterip)) {
            return false;
        }
        if (!Objects.equals(this.externalip, other.externalip)) {
            return false;
        }
        if (!Objects.equals(this.port, other.port)) {
            return false;
        }
        if (!Objects.equals(this.age, other.age)) {
            return false;
        }
        if (!Objects.equals(this.selector, other.selector)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    // Information
    @Override
    public String toString() {
        return "Service{" + "id=" + id + ", namespace=" + namespace + ", name=" + name + ", type=" + type + ", clusterip=" + clusterip + ", externalip=" + externalip + ", port=" + port + ", age=" + age + ", selector=" + selector + '}';
    }

}
