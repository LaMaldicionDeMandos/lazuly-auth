package co.lazuly.auth.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by boot on 12/12/2017.
 */
@Entity
public class Role {
    @Id
    private final String code;

    @NotNull
    private final String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Permission> permissions;

    public Role() {
        code = null;
        name = null;
        permissions = null;
    }

    public Role(final String code, final String name, final Set<Permission> permissions) {
        this.code = code;
        this.name = name;
        this.permissions = permissions;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (code != null ? !code.equals(role.code) : role.code != null) return false;
        return name != null ? name.equals(role.name) : role.name == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Role{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
