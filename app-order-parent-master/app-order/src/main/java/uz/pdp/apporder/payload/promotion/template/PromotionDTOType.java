package uz.pdp.apporder.payload.promotion.template;

import uz.pdp.apporder.payload.promotion.BonusProductPromotionDTO;
import uz.pdp.apporder.payload.promotion.DeliveryPromotionDTO;
import uz.pdp.apporder.payload.promotion.DiscountPromotionDTO;
import uz.pdp.apporder.payload.promotion.ProductPromotionDTO;

public interface PromotionDTOType {

    Long getId();

    Long getStartDate();

    Long getEndDate();

    DeliveryPromotionDTO getDeliveryPromotion();

    ProductPromotionDTO getProductPromotion();

    DiscountPromotionDTO getDiscountPromotion();

    BonusProductPromotionDTO getBonusProductPromotion();

    void setId(Long id);

    void setStartDate(Long startDate);

    void setEndDate(Long endDate);

    void setDeliveryPromotion(DeliveryPromotionDTO deliveryPromotion);

    void setProductPromotion(ProductPromotionDTO productPromotion);

    void setDiscountPromotion(DiscountPromotionDTO discountPromotion);

    void setBonusProductPromotion(BonusProductPromotionDTO bonusProductPromotion);
}
