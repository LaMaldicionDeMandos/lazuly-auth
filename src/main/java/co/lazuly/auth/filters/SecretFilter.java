package co.lazuly.auth.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.GenericFilterBean;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by boot on 14/12/2017.
 */
public class SecretFilter extends GenericFilterBean {
    private final static Logger log = LoggerFactory.getLogger(SecretFilter.class);
    private final static String SECRET_HEADER = "X-Authorization-Secret";

    private final String secret;

    public SecretFilter(final String secret) {
        this.secret = secret;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        RequestWrapper request = new RequestWrapper((HttpServletRequest) req);
        HttpServletResponse response = (HttpServletResponse) res;

        if (shouldDoFilter(request)) {
            doFilter(request);
        }

        chain.doFilter(request, response);
    }

    private boolean shouldDoFilter(final RequestWrapper request) {
        final String path = request.getServletPath();
        return path.startsWith("/users") && HttpMethod.PATCH.matches(request.getMethod());
    }

    private void doFilter(final RequestWrapper request) throws AuthenticationException {
        String header = request.getHeader(SECRET_HEADER);
        log.info("Secret header: {}", header);
        log.info("Secret local: {}", secret);
        if (!secret.equals(header)) {
            throw new AuthenticationException("Invalid secret.");
        }
    }

}
