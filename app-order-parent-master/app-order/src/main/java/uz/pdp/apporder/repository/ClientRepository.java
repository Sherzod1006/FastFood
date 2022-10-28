package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.apporder.entity.ClientAddress;

@Repository
public interface ClientRepository extends JpaRepository<ClientAddress,Long> {
}
