package co.lazuly.auth.services;

import co.lazuly.auth.model.Role;
import co.lazuly.auth.model.School;
import co.lazuly.auth.model.User;
import co.lazuly.auth.repositories.UserRepository;
import co.lazuly.auth.restclients.EmailRestClient;
import co.lazuly.auth.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.Arrays.asList;

/**
 * Created by boot on 12/12/2017.
 */
@Service
public class UserService implements UserDetailsService {
    private final RoleService roleService;
    private final UserRepository repo;
    private final EmailRestClient emailService;
    private final List<String> supportEmails;
    private Role owner;

    @Autowired
    public UserService(final RoleService roleService, final UserRepository repo, final EmailRestClient emailService,
                       @Value("#{'${app.emails.support}'.split(',')}") final List<String> supportEmails) {
        this.roleService = checkNotNull(roleService);
        this.repo = checkNotNull(repo);
        this.emailService = checkNotNull(emailService);
        this.supportEmails = supportEmails;
        this.owner = roleService.getOwner();
    }

    public User createOwner(final String email, final String firstName, final String lastName, final String password,
                            final School school) {
        User user = new User(email, firstName, lastName, password, school, asList(getOwner()));
        user = repo.save(user);

        //TODO hay que implementar los datos que van a ir en el email
        sendEmails(user);

        return user;
    }

    private void sendEmails(final User user) {
        EmailRestClient.Email userEmail = new EmailRestClient.Email("LALA", user.getEmail(), newHashMap());
        EmailRestClient.Email supportEmail = new EmailRestClient.Email("LOLOLO", supportEmails, newHashMap());

        emailService.send(userEmail);
        emailService.send(supportEmail);
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
