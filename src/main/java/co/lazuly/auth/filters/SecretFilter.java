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
import java.util.Arrays;

import static com.google.common.collect.Iterables.any;

/**
 * Created by boot on 14/12/2017.
 */
public class SecretFilter extends GenericFilterBean {
    private final static Logger log = LoggerFactory.getLogger(SecretFilter.class);
    private final static String SECRET_HEADER = "X-Authorization-Secret";

    private final String secret;

    private final Iterable<ShouldDoFilter> filters;

    private interface ShouldDoFilter {
        boolean should(final String path, final String method);
    }

    public SecretFilter(final String secret) {
        this.secret = secret;
        filters = Arrays.asList(
                (String path, String method) -> path.startsWith("/users") && HttpMethod.PATCH.matches(method),
                (String path, String method) -> path.startsWith("/users/") &&
                        !path.contains("registration") &&
                        HttpMethod.POST.matches(method),
                (String path, String method) -> path.startsWith("/roles") && HttpMethod.GET.matches(method));
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        RequestWrapper request = new RequestWrapper((HttpServletRequest) req);
        HttpServletResponse response = (HttpServletResponse) res;

        if (shouldDoFilter(request) && doFilter(request)) {
            ((HttpServletResponse) res).sendError(403, "Forbidden");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean shouldDoFilter(final RequestWrapper request) {
        final String path = request.getServletPath();
        final String method = request.getMethod();
        return any(filters, (ShouldDoFilter filter) -> filter.should(path, method));
    }

    private boolean doFilter(final RequestWrapper request) throws AuthenticationException {
        String header = request.getHeader(SECRET_HEADER);
        log.info("Secret header: {}", header);
        log.info("Secret local: {}", secret);
        return !secret.equals(header);

    }

}
