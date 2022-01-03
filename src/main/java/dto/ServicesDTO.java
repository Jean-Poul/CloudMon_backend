
package dto;


import entities.Service;
import java.util.ArrayList;
import java.util.List;

public class ServicesDTO {
    
List<ServiceDTO> all = new ArrayList();

    // Constructor
    public ServicesDTO(List<Service> serviceEntities) {
        serviceEntities.forEach((service) -> {
            all.add(new ServiceDTO(service));
        });
    }

    // Getters and setters
    public List<ServiceDTO> getAll() {
        return all;
    }

    public void setAll(List<ServiceDTO> all) {
        this.all = all;
    }  

}
