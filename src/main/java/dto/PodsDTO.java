
package dto;

import entities.Pod;
import java.util.ArrayList;
import java.util.List;

public class PodsDTO {

    List<PodDTO> all = new ArrayList();

    // Constructor
    public PodsDTO(List<Pod> podEntities) {
        podEntities.forEach((pod) -> {
            all.add(new PodDTO(pod));
        });
    }

    // Getters and setters
    public List<PodDTO> getAll() {
        return all;
    }

    public void setAll(List<PodDTO> all) {
        this.all = all;
    }

}
