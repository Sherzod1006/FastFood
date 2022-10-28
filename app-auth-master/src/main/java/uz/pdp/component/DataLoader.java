package uz.pdp.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.entity.Employee;
import uz.pdp.entity.Page;
import uz.pdp.entity.Role;
import uz.pdp.entity.User;
import uz.pdp.entity.enums.PageEnum;
import uz.pdp.entity.enums.PermissionEnum;
import uz.pdp.entity.enums.RoleTypeEnum;
import uz.pdp.exceptions.RestException;
import uz.pdp.repository.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final PageRepository pageRepository;
    private final ClientRepository clientRepository;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlMode;

    @Value("${app.admin.phoneNumber}")
    private String adminPhoneNumber;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (Objects.equals(ddlMode, "create") ||
                Objects.equals(ddlMode, "create-drop")) {
            User admin = new User(
                    adminPhoneNumber,
                    passwordEncoder.encode(adminPassword));
            admin.setEnabled(true);
            admin = userRepository.save(admin);

            Role roleSuperAdmin = new Role();
            roleSuperAdmin.setName("SUPER_ADMIN");
            roleSuperAdmin.setRoleType(RoleTypeEnum.ADMIN);
            roleSuperAdmin.setDescription("Owner of this project");
            roleSuperAdmin.setPermissions(Set.of(PermissionEnum.values()));


            roleSuperAdmin = roleRepository.save(roleSuperAdmin);

            Role finalRole = roleSuperAdmin;
            AtomicInteger k = new AtomicInteger(1);

            Set<Page> pages = new HashSet<>(
                    pageRepository
                            .saveAll(
                                    Arrays.stream(PageEnum.values())
                                            .map(pageEnum -> new Page(pageEnum, finalRole, k.getAndIncrement()))
                                            .collect(Collectors.toSet())));

            roleSuperAdmin.setPages(pages);
            roleSuperAdmin = roleRepository.save(roleSuperAdmin);
            Employee superAdmin = new Employee();
            superAdmin.setUser(admin);
            superAdmin.setRole(roleSuperAdmin);
            superAdmin.setFirstName("Super");
            superAdmin.setLastName("Admin");
            employeeRepository.save(superAdmin);
        }
        clientRepository.executeInitialFunction();

        Role role = roleRepository
                .findByRoleType(RoleTypeEnum.ADMIN)
                .orElseThrow(() ->
                        RestException
                                .restThrow(
                                        "Admin role bo'lmasa o'lasan",
                                        HttpStatus.BAD_REQUEST));
        role.setPermissions(Set.of(PermissionEnum.values()));
        roleRepository.save(role);
    }


}
