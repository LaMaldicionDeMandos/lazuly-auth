package co.lazuly.auth.repositories;

import co.lazuly.auth.model.Permission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by boot on 12/12/2017.
 */
@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {
}
