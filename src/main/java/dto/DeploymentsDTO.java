
package dto;

import entities.Deployment;
import java.util.ArrayList;
import java.util.List;

public class DeploymentsDTO {

    List<DeploymentDTO> all = new ArrayList();

    // Constructor
    public DeploymentsDTO(List<Deployment> deploymentEntities) {
        deploymentEntities.forEach((deployment) -> {
            all.add(new DeploymentDTO(deployment));
        });
    }

    // Getters and setters
    public List<DeploymentDTO> getAll() {
        return all;
    }

    public void setAll(List<DeploymentDTO> all) {
        this.all = all;
    }

}
