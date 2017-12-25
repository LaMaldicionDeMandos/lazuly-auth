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
import java.util.Map;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;

/**
 * Created by boot on 12/12/2017.
 */
@Service
public class UserService implements UserDetailsService {
    private final RoleService roleService;
    private final UserRepository repo;
    private final EmailRestClient emailService;
    private final List<String> supportEmails;
    private final String welcomeEmailCode;
    private final String newSchoolEmailCode;

    private Role owner;

    @Autowired
    public UserService(final RoleService roleService, final UserRepository repo, final EmailRestClient emailService,
                       @Value("#{'${app.emails.support}'.split(',')}") final List<String> supportEmails,
                       @Value("${app.mailjet.welcome}") final String welcomeEmailCode,
                       @Value("${app.mailjet.new}") final String newSchoolEmailCode) {
        this.roleService = checkNotNull(roleService);
        this.repo = checkNotNull(repo);
        this.emailService = checkNotNull(emailService);
        this.welcomeEmailCode = checkNotNull(welcomeEmailCode);
        this.newSchoolEmailCode = checkNotNull(newSchoolEmailCode);
        this.supportEmails = supportEmails;
        this.owner = roleService.getOwner();
    }

    public User createOwner(final String email, final String firstName, final String lastName, final String password,
                            final School school) {
        User user = new User(email, firstName, lastName, password, school, asList(getOwner()));
        user = repo.save(user);

        sendEmails(user, password);

        return user;
    }

    public User createUser(final String email, final String firstName, final String lastName, final String role,
                           final School school) {
        User user = new User(email, firstName, lastName, "PASSWORD", school, asList(getRole(role)));
        //user = repo.save(user);

        //sendEmails(user, password);

        return user;
    }

    private void sendEmails(final User user, final String password) {
        EmailRestClient.Email userEmail = new EmailRestClient.Email(welcomeEmailCode, user.getEmail(),
                createUserPayload(user, password));
        EmailRestClient.Email supportEmail = new EmailRestClient.Email(newSchoolEmailCode, supportEmails,
                createSchoolPayload(user));

        emailService.send(userEmail);
        emailService.send(supportEmail);
    }

    private Map<String, String> createUserPayload(final User user, final String password) {
        Map<String, String> payload = newHashMap();
        payload.put("full_name", user.fullName());
        payload.put("school_domain", user.getSchool().getDomain());
        payload.put("password", password);
        return payload;
    }

    private Map<String, String> createSchoolPayload(final User user) {
        Map<String, String> payload = newHashMap();
        payload.put("username", user.getEmail());
        payload.put("full_name", user.fullName());
        payload.put("school_name", user.getSchool().getName());
        payload.put("school_domain", user.getSchool().getDomain());
        return payload;
    }

    private Role getOwner() {
        if (isNull(owner)) {
            owner = roleService.getOwner();
        }
        return owner;
    }

    private Role getRole(final String role) {
        return roleService.getRole(role);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByEmail(username);
        if (isNull(user)) throw new UsernameNotFoundException(username);
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(user);
        return authenticatedUser;
    }

    User findUser(final String email) {
        User user = repo.findByEmail(email);
        if (isNull(user)) throw new UsernameNotFoundException(email);
        return user;
    }

    public User changePassword(final String email, final String newPassword) {
        User user = findUser(email);
        user.newPassword(newPassword);
        return repo.save(user);
    }
}
