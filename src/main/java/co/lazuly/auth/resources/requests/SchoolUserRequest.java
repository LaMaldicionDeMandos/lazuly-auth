package co.lazuly.auth.resources.requests;

/**
 * Created by boot on 25/12/2017.
 */
public class SchoolUserRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String rol;

    public SchoolUserRequest() {
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.rol = null;
    }

    public SchoolUserRequest(String firstName, String lastName, String email, String rol) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.rol = rol;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getRol() {
        return rol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchoolUserRequest that = (SchoolUserRequest) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return rol != null ? rol.equals(that.rol) : that.rol == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (rol != null ? rol.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SchoolUserRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
