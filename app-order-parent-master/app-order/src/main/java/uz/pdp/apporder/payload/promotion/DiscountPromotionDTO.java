package uz.pdp.apporder.payload.promotion;

import lombok.*;
import uz.pdp.apporder.entity.promotion.DiscountPromotion;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountPromotionDTO {

    private Integer id;

    private Float moreThan;

    private Float discount;

    public DiscountPromotion toDiscountPromotion(DiscountPromotionDTO discountPromotionDTO){
        return new DiscountPromotion(
                discountPromotionDTO.getId(),
                discountPromotionDTO.getMoreThan(),
                discountPromotionDTO.getDiscount()
        );
    }

    public DiscountPromotionDTO toDiscountPromotionDTO(DiscountPromotion discountPromotion){
        return DiscountPromotionDTO
                .builder()
                .id(discountPromotion.getId())
                .discount(discountPromotion.getDiscount())
                .moreThan(discountPromotion.getMoreThan())
                .build();
    }

}
