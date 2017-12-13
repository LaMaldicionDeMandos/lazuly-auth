package co.lazuly.auth.restclients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Created by boot on 10/19/17.
 */
@Component
public class EmailRestClient {
    private final static Logger log = LoggerFactory.getLogger(EmailRestClient.class);
    private final static String FIND_TEMPLATE = "%1$s/api/email";
    private final static String SECRET_HEADER = "X-Authorization-Secret";

    public static class Email {
        private final String code;
        private final List<String> to;
        private final Map<String, String> payload;

        public Email(final String code, final String to, Map<String, String> payload) {
            this(code, asList(to), payload);
        }

        public Email(final String code, final List<String> to, Map<String, String> payload) {
            this.code = code;
            this.to = to;
            this.payload = payload;
        }

        public String getCode() {
            return code;
        }

        public List<String> getTo() {
            return to;
        }

        public Map<String, String> getPayload() {
            return payload;
        }
    }
    @Autowired
    RestTemplate restTemplate;

    @Value("${app.host}")
    String host;

    @Value("${app.secret}")
    String secret;

    //TODO Ver que objeto devuelve, se tiene que implementar al impleentar el servicio de email
    public Object send(final Email email) {
        /*
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.add(SECRET_HEADER, secret);

        HttpEntity<Email> entity = new HttpEntity<>(email, headers);

        ResponseEntity<Object> restExchange =
                restTemplate.exchange(format(FIND_TEMPLATE, host), POST, entity, Object.class);
        return restExchange.getBody();
        */
        return null;
    }

}
