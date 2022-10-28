package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apporder.entity.promotion.DeliveryPromotion;

public interface DeliveryPromotionRepository extends JpaRepository<DeliveryPromotion, Integer> {

}
