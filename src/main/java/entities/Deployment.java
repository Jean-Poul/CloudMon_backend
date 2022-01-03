
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
@Table(name = "deployments")
@NamedQuery(name = "Deployment.deleteAllRows", query = "DELETE from Deployment")
@NamedQuery(name = "Deployment.getAllRows", query = "SELECT d from Deployment d")
public class Deployment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namespace;
    private String name;
    private String ready;
    private String uptodate;
    private String available;
    private String age;
    private String containers;
    private String images;
    private String selector;

    // Constructors
    public Deployment() {

    }

    public Deployment(String namespace, String name, String ready, String uptodate, String available, String age, String containers, String images, String selector) {
        this.namespace = namespace;
        this.name = name;
        this.ready = ready;
        this.uptodate = uptodate;
        this.available = available;
        this.age = age;
        this.containers = containers;
        this.images = images;
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

    public String getReady() {
        return ready;
    }

    public void setReady(String ready) {
        this.ready = ready;
    }

    public String getUptodate() {
        return uptodate;
    }

    public void setUptodate(String uptodate) {
        this.uptodate = uptodate;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getContainers() {
        return containers;
    }

    public void setContainers(String containers) {
        this.containers = containers;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.namespace);
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.ready);
        hash = 53 * hash + Objects.hashCode(this.uptodate);
        hash = 53 * hash + Objects.hashCode(this.available);
        hash = 53 * hash + Objects.hashCode(this.age);
        hash = 53 * hash + Objects.hashCode(this.containers);
        hash = 53 * hash + Objects.hashCode(this.images);
        hash = 53 * hash + Objects.hashCode(this.selector);
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
        final Deployment other = (Deployment) obj;
        if (!Objects.equals(this.namespace, other.namespace)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.ready, other.ready)) {
            return false;
        }
        if (!Objects.equals(this.uptodate, other.uptodate)) {
            return false;
        }
        if (!Objects.equals(this.available, other.available)) {
            return false;
        }
        if (!Objects.equals(this.age, other.age)) {
            return false;
        }
        if (!Objects.equals(this.containers, other.containers)) {
            return false;
        }
        if (!Objects.equals(this.images, other.images)) {
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
        return "Deployment{" + "id=" + id + ", namespace=" + namespace + ", name=" + name + ", ready=" + ready + ", uptodate=" + uptodate + ", available=" + available + ", age=" + age + ", containers=" + containers + ", images=" + images + ", selector=" + selector + '}';
    }

}
