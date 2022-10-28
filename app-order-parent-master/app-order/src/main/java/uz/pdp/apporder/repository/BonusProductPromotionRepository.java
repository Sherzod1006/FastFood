package uz.pdp.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apporder.entity.promotion.BonusProductPromotion;

public interface BonusProductPromotionRepository extends JpaRepository<BonusProductPromotion, Integer> {

}
