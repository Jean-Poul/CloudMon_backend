package dto;

import entities.User;
import java.util.Date;
import java.util.Objects;

public class UserDTO {

    private String userID;
    private String password;
    private Date lastLoginTime;

    // Constructor
    public UserDTO(User u) {
        this.userID = u.getUserName();
        this.password = u.getUserPass();
        this.lastLoginTime = u.getLast_loginDate();
    }

    // Getters and setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    // For test purpose
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.userID);
        hash = 59 * hash + Objects.hashCode(this.password);
        hash = 59 * hash + Objects.hashCode(this.lastLoginTime);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserDTO other = (UserDTO) obj;
        if (!Objects.equals(this.userID, other.userID)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.lastLoginTime, other.lastLoginTime)) {
            return false;
        }
        return true;
    }



}
