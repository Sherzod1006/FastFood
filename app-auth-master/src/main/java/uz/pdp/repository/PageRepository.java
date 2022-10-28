package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.entity.Page;

/**
 * Me: muhammadqodir
 * Project: app-auth/IntelliJ IDEA
 * Date:Tue 11/10/22 16:25
 */
@Repository
public interface PageRepository extends JpaRepository<Page, Long> {
}
