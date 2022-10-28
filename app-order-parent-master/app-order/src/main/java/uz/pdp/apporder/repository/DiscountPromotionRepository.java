package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apporder.entity.promotion.DiscountPromotion;

public interface DiscountPromotionRepository extends JpaRepository<DiscountPromotion, Integer> {

}
