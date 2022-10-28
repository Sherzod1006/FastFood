package uz.pdp.apporder.entity.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.template.AbsLongEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Promotion extends AbsLongEntity {

    @Column(nullable = false)
    private Long startDate;

    @Column(nullable = false)
    private Long endDate;

    @ManyToOne
    private DeliveryPromotion deliveryPromotion;

    @ManyToOne
    private ProductPromotion productPromotion;

    @ManyToOne
    private DiscountPromotion discountPromotion;

    @ManyToOne
    private BonusProductPromotion bonusProductPromotion;

}
