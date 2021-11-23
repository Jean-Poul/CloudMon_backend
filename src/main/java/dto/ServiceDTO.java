/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Service;
import java.util.Objects;

/**
 *
 * @author jplm
 */
public class ServiceDTO {

    private Long id;

    private Service service;

    private String namespace;
    private String name;
    private String type;
    private String clusterip;
    private String externalip;
    private String port;
    private String age;
    private String selector;

    public ServiceDTO(Service service) {
        this.id = service.getId();
        this.namespace = service.getNamespace();
        this.name = service.getName();
        this.type = service.getType();
        this.clusterip = service.getClusterip();
        this.externalip = service.getExternalip();
        this.port = service.getPort();
        this.age = service.getAge();
        this.selector = service.getSelector();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
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

// skal nok slettes ellers bruges til test
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.service);
        hash = 37 * hash + Objects.hashCode(this.namespace);
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.type);
        hash = 37 * hash + Objects.hashCode(this.clusterip);
        hash = 37 * hash + Objects.hashCode(this.port);
        hash = 37 * hash + Objects.hashCode(this.age);
        hash = 37 * hash + Objects.hashCode(this.selector);
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
        final ServiceDTO other = (ServiceDTO) obj;
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
        if (!Objects.equals(this.service, other.service)) {
            return false;
        }
        return true;
    }

}
