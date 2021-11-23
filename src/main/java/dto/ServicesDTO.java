/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;


import entities.Service;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jplm
 */
public class ServicesDTO {
    
List<ServiceDTO> all = new ArrayList();

    public ServicesDTO(List<Service> serviceEntities) {
        serviceEntities.forEach((service) -> {
            all.add(new ServiceDTO(service));
        });
    }

    public List<ServiceDTO> getAll() {
        return all;
    }

    public void setAll(List<ServiceDTO> all) {
        this.all = all;
    }  

}
