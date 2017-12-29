package co.lazuly.auth.resources;

import co.lazuly.auth.model.Role;
import co.lazuly.auth.repositories.RoleRepository;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    @RequestMapping(value = "{code}", method = RequestMethod.GET)
    ResponseEntity<Role> get(@PathVariable final String code) {
        log.info("Getting role {}", code);
        Role role = repo.findOne(code);
        return ResponseEntity.ok(role);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<Iterable<Role>> get(@RequestParam(required = false) final List<String> codes) {
        log.info("Getting roles for {}", codes);

        Iterable<Role> roles = (isNull(codes) || codes.isEmpty())
                ? repo.findAll() : repo.findAll(codes);
        return ResponseEntity.ok(roles);
    }

}
