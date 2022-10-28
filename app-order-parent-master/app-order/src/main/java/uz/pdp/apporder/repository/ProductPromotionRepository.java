package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apporder.entity.promotion.ProductPromotion;

public interface ProductPromotionRepository extends JpaRepository<ProductPromotion, Integer> {
}
