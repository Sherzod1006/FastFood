package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.entity.Client;
import uz.pdp.payload.filterPayload.ClientDTOView;
import uz.pdp.util.RestConstants;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    Optional<Client> findByUserId(UUID id);
    Optional<Client> findByUser_PhoneNumber(String user_phoneNumber);
    boolean existsByUser_PhoneNumber(String user_phoneNumber);

    @Query(value = "SELECT * FROM get_query_result(?1)", nativeQuery = true)
    List<ClientDTOView> getAllUsersByStringQuery(String query);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = RestConstants.INITIAL_FILTERING_FUNCTION)
    void executeInitialFunction();
}
