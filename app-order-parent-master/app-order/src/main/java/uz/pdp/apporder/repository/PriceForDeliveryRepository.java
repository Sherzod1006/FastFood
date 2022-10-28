package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.apporder.entity.Branch;
import uz.pdp.apporder.entity.PriceForDelivery;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface PriceForDeliveryRepository extends JpaRepository<PriceForDelivery, Integer> {
    boolean existsByBranchContains(String name);

    Optional<PriceForDelivery> findByBranch(@NotNull Branch branch);
}
