package co.lazuly.auth.resources;

import co.lazuly.auth.model.Role;
import co.lazuly.auth.repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by boot on 12/12/2017.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "roles", produces = APPLICATION_JSON_VALUE)
public class RoleResources {
    Logger log = LoggerFactory.getLogger(RoleResources.class);

    @Autowired
    RoleRepository repo;

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    ResponseEntity<Role> get(@PathVariable final String code) {
        log.info("Get Role {}", code);
        Role role = repo.findOne(code);
        if (isNull(role)) return notFound().build();
        return ok(role);
    }
}
