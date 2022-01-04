
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
@Table(name = "pods")
@NamedQuery(name = "Pod.deleteAllRows", query = "DELETE from Pod")
@NamedQuery(name = "Pod.getAllRows", query = "SELECT p from Pod p")
public class Pod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namespace;
    private String name;
    private String ready;
    private String status;
    private String restarts;
    private String age;
    private String ip;
    private String node;

    // Constructors
    public Pod() {

    }

    public Pod(String namespace, String name, String ready, String status, String restarts, String age, String ip, String node) {
        this.namespace = namespace;
        this.name = name;
        this.ready = ready;
        this.status = status;
        this.restarts = restarts;
        this.age = age;
        this.ip = ip;
        this.node = node;
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

    public String getReady() {
        return ready;
    }

    public void setReady(String ready) {
        this.ready = ready;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRestarts() {
        return restarts;
    }

    public void setRestarts(String restarts) {
        this.restarts = restarts;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    // For testing purpose
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.namespace);
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.ready);
        hash = 97 * hash + Objects.hashCode(this.status);
        hash = 97 * hash + Objects.hashCode(this.restarts);
        hash = 97 * hash + Objects.hashCode(this.age);
        hash = 97 * hash + Objects.hashCode(this.ip);
        hash = 97 * hash + Objects.hashCode(this.node);
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
        final Pod other = (Pod) obj;
        if (!Objects.equals(this.namespace, other.namespace)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.ready, other.ready)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.restarts, other.restarts)) {
            return false;
        }
        if (!Objects.equals(this.age, other.age)) {
            return false;
        }
        if (!Objects.equals(this.ip, other.ip)) {
            return false;
        }
        if (!Objects.equals(this.node, other.node)) {
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
        return "Pod{" + "id=" + id + ", namespace=" + namespace + ", name=" + name + ", ready=" + ready + ", status=" + status + ", restarts=" + restarts + ", age=" + age + ", ip=" + ip + ", node=" + node + '}';
    }

}
