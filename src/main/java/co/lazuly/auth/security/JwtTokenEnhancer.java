package co.lazuly.auth.security;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by boot on 7/8/17.
 */
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        Long schoolId = user.getSchool().getId();
        String schoolName = user.getSchool().getName();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        additionalInfo.put("school_id", schoolId);
        additionalInfo.put("school_name", schoolName);
        additionalInfo.put("first_name", firstName);
        additionalInfo.put("last_name", lastName);
        additionalInfo.put("roles", user.getRoles());
        additionalInfo.put("email", user.getUsername());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
