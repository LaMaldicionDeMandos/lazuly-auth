package co.lazuly.auth.streaming;

import co.lazuly.auth.resources.requests.SchoolUserRequest;

import java.util.List;

/**
 * Created by boot on 26/12/2017.
 */
public class NewUser extends SchoolUserRequest {
    private final Long schoolId;

    public NewUser() {
        super();
        this.schoolId = null;
    }

    public NewUser(String firstName, String lastName, String email, List<String> roles, Long schoolId) {
        super(firstName, lastName, email, roles);
        this.schoolId = schoolId;
    }

    public Long getSchoolId() {
        return schoolId;
    }
}
