package co.lazuly.auth.security;

import co.lazuly.auth.filters.SecretFilter;
import co.lazuly.auth.repositories.PermissionRepository;
import co.lazuly.auth.services.RoleService;
import co.lazuly.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.Filter;

/**
 * Created by boot on 7/8/17.
 */
@Configuration
public class WebSecurutyConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    RoleService roleService;

    @Autowired
    LazulyAuthenticationProvider authProvider;

    @Autowired
    private UserService userDetailsService;

    @Value("${app.secret}")
    private String secret;

    @Bean
    public Filter secretFilter() {
        SecretFilter secretFilter = new SecretFilter(secret);
        return secretFilter;
    }

    @Bean
    public FilterRegistrationBean secretFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(secretFilter());
        return registration;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return userDetailsService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        roleService.loadCommondRoles();
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }
}
