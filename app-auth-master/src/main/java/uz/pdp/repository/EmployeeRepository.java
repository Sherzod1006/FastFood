package uz.pdp.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Employee;
import uz.pdp.entity.Role;
import uz.pdp.entity.User;

import java.util.Optional;
import java.util.UUID;

@Configuration
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    boolean existsByUserId(UUID user_id);

    Optional<Employee> findEmployeeByUserId(UUID uuid);

    Optional<Employee> findByUserId(UUID user_id);
    Optional<Employee> findByUser(User user);

    boolean existsByUser_PhoneNumber(String user_phoneNumber);

   Employee findByUser_PhoneNumber(String user_phoneNumber);

}
