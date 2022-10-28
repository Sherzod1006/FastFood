package uz.pdp.apporder.payload.promotion;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import uz.pdp.apporder.payload.promotion.template.PromotionDTOType;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PromotionDTO implements PromotionDTOType {

    @NotNull
    private Long id;

    @NotNull
    private Long startDate;

    @NotNull
    private Long endDate;

    private DeliveryPromotionDTO deliveryPromotion;

    private ProductPromotionDTO productPromotion;

    private DiscountPromotionDTO discountPromotion;

    private BonusProductPromotionDTO bonusProductPromotion;


    public void setId(Long id) {
        this.id = id;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public void setDeliveryPromotion(DeliveryPromotionDTO deliveryPromotion) {
        this.deliveryPromotion = deliveryPromotion;
    }

    public void setProductPromotion(ProductPromotionDTO productPromotion) {
        this.productPromotion = productPromotion;
    }

    public void setDiscountPromotion(DiscountPromotionDTO discountPromotion) {
        this.discountPromotion = discountPromotion;
    }

    public void setBonusProductPromotion(BonusProductPromotionDTO bonusProductPromotion) {
        this.bonusProductPromotion = bonusProductPromotion;
    }
}
