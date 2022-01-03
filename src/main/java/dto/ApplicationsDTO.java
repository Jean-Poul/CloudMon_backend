
package dto;

import entities.Application;
import java.util.ArrayList;
import java.util.List;

public class ApplicationsDTO {

    List<ApplicationDTO> all = new ArrayList();

    // Constructor
    public ApplicationsDTO(List<Application> appEntities) {
        appEntities.forEach((app) -> {
            all.add(new ApplicationDTO(app));
        });
    }

    // Getters and setters
    public List<ApplicationDTO> getAll() {
        return all;
    }

    public void setAll(List<ApplicationDTO> all) {
        this.all = all;
    }

}
