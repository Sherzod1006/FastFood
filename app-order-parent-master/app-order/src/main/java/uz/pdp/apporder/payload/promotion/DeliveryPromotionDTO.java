package uz.pdp.apporder.payload.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.promotion.DeliveryPromotion;

import javax.persistence.Column;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPromotionDTO {

    private Integer id;

    @Column
    private Float moreThan;


    private Long startTime;

    private Long endTime;

    public DeliveryPromotion toDeliveryPromotion(DeliveryPromotionDTO deliveryPromotionDTO) {
        return new DeliveryPromotion(
                deliveryPromotionDTO.getId(),
                deliveryPromotionDTO.moreThan,
                deliveryPromotionDTO.startTime,
                deliveryPromotionDTO.endTime
        );
    }

    public DeliveryPromotionDTO toDeliveryPromotionDTO(DeliveryPromotion deliveryPromotion) {
        return new DeliveryPromotionDTO(
                deliveryPromotion.getId(),
                deliveryPromotion.getMoreThan(),
                deliveryPromotion.getStartTime(),
                deliveryPromotion.getEndTime()
        );
    }

}
