package co.lazuly.auth.security;

import co.lazuly.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

/**
 * Created by boot on 7/8/17.
 */
@Configuration
public class JwtOauth2Config extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private DefaultTokenServices tokenServices;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenEnhancer jwtTokenEnhancer;

    @Value("${app.security.clientkey}")
    private String clientKey;

    @Value("${app.security.expiration}")
    private int expiration;

    @Value("${app.security.refreshExpiration}")
    private int refreshExpiration;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtTokenEnhancer, jwtAccessTokenConverter));
        endpoints.tokenStore(tokenStore)                             //JWT
                .accessTokenConverter(jwtAccessTokenConverter)       //JWT
                .tokenEnhancer(tokenEnhancerChain)                   //JWT
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }



    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("lazuly")
                .secret(clientKey)
                .authorizedGrantTypes("refresh_token", "password", "client_credentials")
                .scopes("web", "mobile")
                .accessTokenValiditySeconds(expiration)
                .refreshTokenValiditySeconds(refreshExpiration);
    }
}
