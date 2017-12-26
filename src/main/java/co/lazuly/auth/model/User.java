package co.lazuly.auth.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * Created by boot on 12/12/2017.
 */
@Entity
@Table(indexes = {@Index(name = "email_idx", columnList = "email")})
public class User {
    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;
    @NotNull
    private final String email;
    @NotNull
    private final String firstName;
    @NotNull
    private final String lastName;
    @NotNull
    private String password;

    private boolean verified;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    private final School school;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private List<Role> roles;

    public User() {
        id = null;
        email = null;
        firstName = null;
        lastName = null;
        password = null;
        verified = false;
        school = null;
        roles = null;
    }

    public User(String email, String firstName, String lastName, String password, School school, List<Role> roles) {
        this.id = null;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = passwordEncoder.encode(password);
        this.verified = false;
        this.school = school;
        this.roles = roles;
    }

    public String newPassword(final String newPassword) {
        setPassword(passwordEncoder.encode(newPassword));
        return getPassword();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public School getSchool() {
        return school;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String fullName() {
        return Objects.toString(firstName, "") + " " + Objects.toString(lastName, "").trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        return school != null ? school.equals(user.school) : user.school == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (school != null ? school.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", verified=" + verified +
                ", school=" + school +
                ", roles=" + roles +
                '}';
    }
}
