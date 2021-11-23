/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Deployment;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jplm
 */
public class DeploymentsDTO {

    List<DeploymentDTO> all = new ArrayList();

    public DeploymentsDTO(List<Deployment> deploymentEntities) {
        deploymentEntities.forEach((deployment) -> {
            all.add(new DeploymentDTO(deployment));
        });
    }

    public List<DeploymentDTO> getAll() {
        return all;
    }

    public void setAll(List<DeploymentDTO> all) {
        this.all = all;
    }

}
