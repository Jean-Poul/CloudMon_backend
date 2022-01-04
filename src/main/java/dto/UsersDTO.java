
package dto;

import entities.User;
import java.util.ArrayList;
import java.util.List;

public class UsersDTO {

    List<UserDTO> all = new ArrayList();

    // Constructor
    public UsersDTO(List<User> userEntities) {
        userEntities.forEach((user) -> {
            all.add(new UserDTO(user));
        });
    }

    // Getters and setters
    public List<UserDTO> getAll() {
        return all;
    }

    public void setAll(List<UserDTO> all) {
        this.all = all;
    }

}
