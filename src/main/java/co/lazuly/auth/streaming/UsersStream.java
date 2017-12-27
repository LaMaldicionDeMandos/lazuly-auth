package co.lazuly.auth.streaming;

import co.lazuly.auth.model.School;
import co.lazuly.auth.repositories.SchoolRepository;
import co.lazuly.auth.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * Created by boot on 26/12/2017.
 */
@EnableBinding(UsersChannels.class)
public class UsersStream {

    @Autowired
    SchoolRepository schoolRepo;

    @Autowired
    UserService service;

    private final Logger logger = LoggerFactory.getLogger(UsersStream.class);

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

    @StreamListener(UsersChannels.DELETE_USER_INPUT)
    public void deleteUser(final String email) {
        logger.info("Receiving delete user {}.", email);
        try {
            service.delete(email);
        } catch (Exception e) {
            logger.info("Error deleting user from stream");
        }
    }

    @StreamListener(UsersChannels.CHANGE_ROLES_INPUT)
    public void deleteUser(final Map<String, Object> payload) {
        logger.info("Receiving changeRoles {}.", payload);
        try {
            String email = payload.get("email").toString();
            List<String> roles = (List<String>) payload.get("roles");
            service.changeRoles(email, roles);
        } catch (Exception e) {
            logger.info("Error deleting user from stream");
        }
    }

}
