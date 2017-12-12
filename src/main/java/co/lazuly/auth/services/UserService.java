package co.lazuly.auth.services;

import co.lazuly.auth.model.Role;
import co.lazuly.auth.model.School;
import co.lazuly.auth.model.User;
import co.lazuly.auth.repositories.UserRepository;
import co.lazuly.auth.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Arrays.asList;

/**
 * Created by boot on 12/12/2017.
 */
@Service
public class UserService implements UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByEmail(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(username);
        }
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(user);
        return authenticatedUser;
    }
}
