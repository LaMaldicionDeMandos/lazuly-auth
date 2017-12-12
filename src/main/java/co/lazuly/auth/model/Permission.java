package co.lazuly.auth.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by boot on 12/12/2017.
 */
@Entity
@Table(indexes = {@Index(name = "name_idx", columnList = "name")})
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;
    @NotNull
    private final String name;

    Permission() {
        id = null;
        name = null;
    }

    public Permission(final String name) {
        this.id = null;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
