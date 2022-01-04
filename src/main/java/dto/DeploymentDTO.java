
package dto;

import entities.Deployment;
import java.util.Objects;

public class DeploymentDTO {

    private long id;

    private Deployment deployment;

    private String namespace;
    private String name;
    private String ready;
    private String uptodate;
    private String available;
    private String age;
    private String containers;
    private String images;
    private String selector;

    // Constructor
    public DeploymentDTO(Deployment deployment) {
        this.id = deployment.getId();
        this.namespace = deployment.getNamespace();
        this.name = deployment.getName();
        this.ready = deployment.getReady();
        this.uptodate = deployment.getUptodate();
        this.available = deployment.getAvailable();
        this.age = deployment.getAge();
        this.containers = deployment.getContainers();
        this.images = deployment.getImages();
        this.selector = deployment.getSelector();
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Deployment getDeployment() {
        return deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
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
        hash = 23 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 23 * hash + Objects.hashCode(this.deployment);
        hash = 23 * hash + Objects.hashCode(this.namespace);
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.ready);
        hash = 23 * hash + Objects.hashCode(this.uptodate);
        hash = 23 * hash + Objects.hashCode(this.available);
        hash = 23 * hash + Objects.hashCode(this.age);
        hash = 23 * hash + Objects.hashCode(this.containers);
        hash = 23 * hash + Objects.hashCode(this.images);
        hash = 23 * hash + Objects.hashCode(this.selector);
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
        final DeploymentDTO other = (DeploymentDTO) obj;
        if (this.id != other.id) {
            return false;
        }
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
        if (!Objects.equals(this.deployment, other.deployment)) {
            return false;
        }
        return true;
    }


}
