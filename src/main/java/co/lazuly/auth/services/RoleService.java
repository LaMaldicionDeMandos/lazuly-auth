package co.lazuly.auth.services;

import co.lazuly.auth.model.Role;
import co.lazuly.auth.repositories.PermissionRepository;
import co.lazuly.auth.model.Permission;
import co.lazuly.auth.repositories.RoleRepository;
import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;

/**
 * Created by boot on 12/12/2017.
 */
@Service
public class RoleService {
    Logger log = LoggerFactory.getLogger(RoleService.class);

    private final PermissionRepository permissionRepo;
    private final RoleRepository roleRepo;

    @Autowired
    public RoleService(final PermissionRepository permissionRepo, final RoleRepository roleRepo) {
        this.permissionRepo = checkNotNull(permissionRepo);
        this.roleRepo = checkNotNull(roleRepo);
    }

    public void loadCommondRoles() {
        long permissionCount = permissionRepo.count();
        log.info("Permissions actived {}", permissionCount);
        if (permissionCount == 0) {
            loadRoles();
        }
    }

    private void loadRoles() {
        Permission sendToStudentAssigned = createPermission("send_notification_to_student_grade_assigned");
        Permission sendToStudentNotAssigned = createPermission("send_notification_to_student_grade_not_assigned");
        Permission sendToAllStudents = createPermission("send_notification_to_all_students");
        Permission userCrud = createPermission("user_crud");
        Permission roleAssignment = createPermission("role_assignment");
        Permission gradeCrud = createPermission("grade_crud");
        Permission gradeAssignment = createPermission("grade_assignment");
        Permission roleCrud = createPermission("role_crud");
        Permission studentCrud = createPermission("student_crud");

        Set<Permission> allPermissions = newHashSet(
                sendToStudentAssigned,
                sendToStudentNotAssigned,
                sendToAllStudents,
                userCrud,
                roleAssignment,
                gradeCrud,
                gradeAssignment,
                roleCrud,
                studentCrud);

        permissionRepo.save(allPermissions);

        Role owner = createRole("owner", "Dueño", allPermissions);
        Role director = createRole("directive", "Directivo", allPermissions);
        Role admin = createRole("admin", "Administrador", userCrud, roleAssignment, gradeCrud,
                gradeAssignment, roleCrud, studentCrud);
        Role teacher = createRole("teacher", "Maestro/Profesor", sendToStudentAssigned);
        Role parent = createRole("parent", "Pariente");

        roleRepo.save(asList(owner, director, admin, teacher, parent));

        Iterable<Role> roles = roleRepo.findAll();

        roles.forEach((role) -> log.info(role.toString()));
    }

    public Role getOwner() {
        return roleRepo.findOne("owner");
    }

    private Role createRole(final String code, final String name, final Permission... permissions) {
        return new Role(code, name, newHashSet(permissions));
    }

    private Role createRole(final String code, final String name, final Set permissions) {
        return new Role(code, name, permissions);
    }

    private Permission createPermission(final String name) {
        return new Permission(name);
    }

}
