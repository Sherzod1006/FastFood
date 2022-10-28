package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.apporder.entity.Branch;
import uz.pdp.apporder.utils.RestConstants;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    boolean existsByName(String name);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = RestConstants.INITIAL_SEARCHING_FUNCTION)
    void executeInitialFunction();

}
