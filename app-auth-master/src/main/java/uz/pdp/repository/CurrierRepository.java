package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.entity.Currier;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurrierRepository extends JpaRepository<Currier, UUID> {

    boolean existsByCarNumber(String carNumber);

    boolean existsByDriverLicense(String driverLicense);

    Optional<Currier> findById(UUID id);

    List<Currier> getCurriersByOnline(boolean online);

//    Optional<Object> findAllByCurrierStatusEnum(String status);

}
