package co.lazuly.auth.streaming;

import co.lazuly.auth.model.School;
import co.lazuly.auth.repositories.SchoolRepository;
import co.lazuly.auth.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import static java.util.Objects.isNull;

/**
 * Created by boot on 26/12/2017.
 */
@EnableBinding(UsersChannels.class)
public class NewUserStream {

    @Autowired
    SchoolRepository schoolRepo;

    @Autowired
    UserService service;

    private final Logger logger = LoggerFactory.getLogger(NewUserStream.class);
    @StreamListener(UsersChannels.NEW_USER_INPUT)
    public void newUser(final NewUser req) {
        logger.info("Receiving new user {}.", req);
        try {
            School school = schoolRepo.findOne(req.getSchoolId());
            if (isNull(school)) return;

            service.createUser(req.getEmail(), req.getFirstName(), req.getLastName(), req.getRoles(), school);
        } catch (Exception e) {
            logger.info("Error saving new user from stream");
        }
    }

}
