
package dto;

import entities.Namespace;
import java.util.ArrayList;
import java.util.List;

public class NamespacesDTO {

    List<NamespaceDTO> all = new ArrayList();

    // Constructor
    public NamespacesDTO(List<Namespace> namespaceEntities) {
        namespaceEntities.forEach((ns) -> {
            all.add(new NamespaceDTO(ns));
        });
    }

    // Getters and setters
    public List<NamespaceDTO> getAll() {
        return all;
    }

    public void setAll(List<NamespaceDTO> all) {
        this.all = all;
    }

}
