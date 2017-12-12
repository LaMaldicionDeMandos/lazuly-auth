package co.lazuly.auth.repositories;

import co.lazuly.auth.model.School;
import co.lazuly.auth.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by boot on 12/12/2017.
 */
@Repository
public interface SchoolRepository extends CrudRepository<School, Long> {
}
