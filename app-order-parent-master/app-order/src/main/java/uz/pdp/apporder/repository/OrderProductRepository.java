package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.apporder.entity.OrderProduct;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    OrderProduct findByOrderId(Long id);

    @Query(value = "WITH temp AS(SELECT quantity, unit_price  FROM order_product WHERE order_id = :orderId )\n" +
            "SELECT sum(quantity* unit_price) FROM temp", nativeQuery = true)
    Double countSumOfOrder(@Param("orderId") Long orderId);
}
