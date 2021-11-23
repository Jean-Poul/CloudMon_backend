/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Application;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jplm
 */
public class ApplicationsDTO {

    List<ApplicationDTO> all = new ArrayList();

    public ApplicationsDTO(List<Application> appEntities) {
        appEntities.forEach((app) -> {
            all.add(new ApplicationDTO(app));
        });
    }

    public List<ApplicationDTO> getAll() {
        return all;
    }

    public void setAll(List<ApplicationDTO> all) {
        this.all = all;
    }

}
