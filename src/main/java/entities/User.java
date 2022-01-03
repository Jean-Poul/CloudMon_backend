package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.deleteAllRows", query = "DELETE from User")
@NamedQuery(name = "User.getAllRows", query = "SELECT u from User u")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name", length = 25)
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_pass")
    private String userPass;
    @JoinTable(name = "user_roles", joinColumns = {
        @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
        @JoinColumn(name = "role_name", referencedColumnName = "role_name")})

    // Relation
    @ManyToMany
    private List<Role> roleList = new ArrayList<>();

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "last_login")
    private Date last_loginDate;

    // List all roles
    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList<>();
        roleList.forEach((role) -> {
            rolesAsStrings.add(role.getRoleName());
        });
        return rolesAsStrings;
    }

    // Constructors
    public User() {
    }

    public User(String userName, String userPass) {
        this.userName = userName;

        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt(12));
    }

    // Bcrypt method for verifying hashed strings
    public boolean verifyPassword(String pw) {
        return (BCrypt.checkpw(pw, this.userPass));
    }

    // Getters and setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return this.userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public void addRole(Role userRole) {
        roleList.add(userRole);
    }

    public Date getLast_loginDate() {
        return last_loginDate;
    }

    public void setLast_loginDate(Date last_loginDate) {
        this.last_loginDate = last_loginDate;
    }

    // For test purpose
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.userName);
        hash = 11 * hash + Objects.hashCode(this.userPass);
        hash = 11 * hash + Objects.hashCode(this.roleList);
        hash = 11 * hash + Objects.hashCode(this.last_loginDate);
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
        final User other = (User) obj;
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.userPass, other.userPass)) {
            return false;
        }
        if (!Objects.equals(this.roleList, other.roleList)) {
            return false;
        }
        if (!Objects.equals(this.last_loginDate, other.last_loginDate)) {
            return false;
        }
        return true;
    }

    // Information
    @Override
    public String toString() {
        return "User{" + "userName=" + userName + ", userPass=" + userPass + ", roleList=" + roleList + ", last_loginDate=" + last_loginDate + '}';
    }

}
