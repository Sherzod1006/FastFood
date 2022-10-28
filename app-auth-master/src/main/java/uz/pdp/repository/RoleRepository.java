package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Role;
import uz.pdp.entity.enums.RoleTypeEnum;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    boolean existsByName(String name);

    List<Role> findAllByRoleType(RoleTypeEnum roleType);
    Optional<Role> findByRoleType(RoleTypeEnum roleType);
}
