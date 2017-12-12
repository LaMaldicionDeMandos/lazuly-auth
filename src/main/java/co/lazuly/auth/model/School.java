package co.lazuly.auth.model;

import com.github.underscore.string.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import static co.lazuly.auth.model.School.Status.WAITING_DOMAIN;

/**
 * Created by boot on 12/12/2017.
 */
@Entity
public class School {
    public enum Status {
        WAITING_DOMAIN,
        ACTIVE,
        UNACTIVE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;
    @NotNull
    private final String name;

    @NotNull
    private final String domain;

    @NotNull
    private Status status;

    School() {
        id = null;
        name = null;
        domain = null;
    }

    public School(String name) {
        this.id = null;
        this.name = name;
        this.domain = $.chain(name).deburr().camelCase().uncapitalize().item();
        this.status = WAITING_DOMAIN;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDomain() {
        return domain;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        School school = (School) o;

        if (id != null ? !id.equals(school.id) : school.id != null) return false;
        if (name != null ? !name.equals(school.name) : school.name != null) return false;
        return domain != null ? domain.equals(school.domain) : school.domain == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (domain != null ? domain.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", domain='" + domain + '\'' +
                ", status=" + status +
                '}';
    }
}
