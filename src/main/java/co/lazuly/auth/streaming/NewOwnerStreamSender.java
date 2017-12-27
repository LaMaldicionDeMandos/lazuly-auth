package co.lazuly.auth.streaming;

import co.lazuly.auth.model.Role;
import co.lazuly.auth.model.User;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by boot on 26/12/2017.
 */
@Component
@EnableBinding(UsersChannels.class)
public class NewOwnerStreamSender {
    private final UsersChannels source;

    private final static Logger logger = LoggerFactory.getLogger(NewOwnerStreamSender.class);

    @Autowired
    public NewOwnerStreamSender(final UsersChannels source) {
        this.source = source;
    }

    public void send(final User user) {
        List<String> roles = Lists.transform(user.getRoles(), Role::getCode);
        NewUser req = new NewUser(user.getFirstName(), user.getLastName(), user.getEmail(), roles,
                user.getSchool().getId());
        logger.debug("Sending Kafka message {}", req);
        source.newOwnerOutput().send(MessageBuilder.withPayload(req).build());
    }
}
