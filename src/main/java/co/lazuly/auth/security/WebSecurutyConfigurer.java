package co.lazuly.auth.security;

import co.lazuly.auth.repositories.PermissionRepository;
import co.lazuly.auth.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by boot on 7/8/17.
 */
@Configuration
public class WebSecurutyConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    RoleService roleService;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        roleService.loadCommondRoles();
        /*
        auth.inMemoryAuthentication()
            .withUser("john").password("password1").roles("USER")
            .and()
            .withUser("william.woodward").password("password2").roles("USER", "ADMIN");
            */
    }
}
