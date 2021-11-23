/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Pod;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jplm
 */
public class PodsDTO {

    List<PodDTO> all = new ArrayList();

    public PodsDTO(List<Pod> podEntities) {
        podEntities.forEach((pod) -> {
            all.add(new PodDTO(pod));
        });
    }

    public List<PodDTO> getAll() {
        return all;
    }

    public void setAll(List<PodDTO> all) {
        this.all = all;
    }

}
