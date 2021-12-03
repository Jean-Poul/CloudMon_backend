package dto;

import entities.User;
import java.util.Date;
//import java.sql.Date;

public class UserDTO {

    private String userID;
    private String password;
    private Date lastLoginTime;

    public UserDTO() {
    }

    public UserDTO(User u) {
        this.userID = u.getUserName();
        this.password = u.getUserPass();
        this.lastLoginTime = u.getLast_loginDate();
    }

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

}
