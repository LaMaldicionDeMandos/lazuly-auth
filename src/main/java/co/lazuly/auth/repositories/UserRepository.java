package co.lazuly.auth.repositories;

import co.lazuly.auth.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by boot on 12/12/2017.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(final String email);
    @Transactional
    void deleteByEmail(final String email);
}
