package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.apporder.entity.Discount;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    boolean existsById(Integer id);

    Optional<Discount> findByProductIdAndEndDateIsAfter(Integer product_id, Long endDate);

    List<Discount> findAllByEndDateIsAfter(Long endDate);

    @Query("select sum(d.product.price * d.discount / 100) from Discount d " +
            "where d.startDate < :currentTime and  d.endDate > :currentTime and d.product.id in :productIds")
    Optional<Float> getOverallSumOfProductsDiscount(List<Integer> productIds, Long currentTime);

}
