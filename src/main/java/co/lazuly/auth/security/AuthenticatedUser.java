package co.lazuly.auth.security;

import co.lazuly.auth.model.Role;
import co.lazuly.auth.model.School;
import co.lazuly.auth.model.User;
import com.google.common.collect.Lists;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by boot on 12/12/2017.
 */
public class AuthenticatedUser implements UserDetails {
    private class Authority implements GrantedAuthority {
        private final String role;

        public Authority(final Role role) {
            this.role = role.getCode();
        }

        @Override
        public String getAuthority() {
            return role;
        }
    }

    private final User user;
    private final Collection<Authority> authorities;

    public AuthenticatedUser() {
        user = null;
        authorities = null;
    }

    public AuthenticatedUser(final User user) {
        this.user = user;
        authorities = Lists.transform(user.getRoles(), (role) -> new Authority(role));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        //TODO cuando haga la parte de subscripcion voy a tener que validar esto
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //TODO cuando haga la parte de subscripcion voy a tener que validar esto
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getSchool() {
        return user.getSchool().getId();
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastName() {
        return user.getLastName();
    }
}
