package co.lazuly.auth.services;

import co.lazuly.auth.model.Role;
import co.lazuly.auth.model.School;
import co.lazuly.auth.model.User;
import co.lazuly.auth.repositories.UserRepository;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Arrays.asList;

/**
 * Created by boot on 12/12/2017.
 */
@Service
public class UserService {
    private final RoleService roleService;
    private final UserRepository repo;
    private Role owner;

    @Autowired
    public UserService(final RoleService roleService, final UserRepository repo) {
        this.roleService = checkNotNull(roleService);
        this.repo = checkNotNull(repo);
        this.owner = roleService.getOwner();
    }

    public User createOwner(final String email, final String firstName, final String lastName, final String password,
                            final School school) {
        User user = new User(email, firstName, lastName, password, school, asList(getOwner()));
        return repo.save(user);
    }

    private Role getOwner() {
        if (Objects.isNull(owner)) {
            owner = roleService.getOwner();
        }
        return owner;
    }
}
