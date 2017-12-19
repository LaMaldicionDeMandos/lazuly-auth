package co.lazuly.auth.resources;

import co.lazuly.auth.model.School;
import co.lazuly.auth.model.User;
import co.lazuly.auth.repositories.SchoolRepository;
import co.lazuly.auth.resources.requests.SchoolRequest;
import co.lazuly.auth.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by boot on 12/12/2017.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "users", produces = APPLICATION_JSON_VALUE)
public class UserResources {
    Logger log = LoggerFactory.getLogger(UserResources.class);

    @Autowired
    SchoolRepository schoolRepo;

    @Autowired
    UserService service;

    @RequestMapping(value = "/registration", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<User> create(@RequestBody SchoolRequest req) {
        log.info("New School");
        School school = schoolRepo.save(new School(req.getSchoolName()));
        User user = service.createOwner(req.getEmail(), req.getFirstName(), req.getLastName(), req.getPassword(), school);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
