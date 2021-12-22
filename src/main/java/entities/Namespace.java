
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "namespaces")
@NamedQuery(name = "Namespace.deleteAllRows", query = "DELETE from Namespace")
@NamedQuery(name = "Namespace.getAllRows", query = "SELECT ns from Namespace ns")
//@NamedQueries({
//    @NamedQuery(name = "Namespace.deleteAllRows", query = "DELETE from Namespace"),
//    @NamedQuery(name = "Namespace.getAllRows", query = "SELECT ns from Namespace ns")})
public class Namespace implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String status;
    private String age;

    public Namespace() {

    }

    public Namespace(String name, String status, String age) {
        this.name = name;
        this.status = status;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    // delete når alt virker
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Namespace)) {
            return false;
        }
        Namespace other = (Namespace) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Namespace[ id=" + id + " ]";
    }

}
