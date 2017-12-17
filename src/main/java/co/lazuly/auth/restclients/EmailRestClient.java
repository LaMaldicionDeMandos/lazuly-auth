package co.lazuly.auth.restclients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Created by boot on 10/19/17.
 */
@Component
public class EmailRestClient {
    private final static Logger log = LoggerFactory.getLogger(EmailRestClient.class);

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
    EmailFeignClient client;

    @Value("${app.secret}")
    String secret;

    public void send(final Email email) {
        int sended = client.send(email, secret);
        log.info("Sended emails: {}", sended);
    }

}
