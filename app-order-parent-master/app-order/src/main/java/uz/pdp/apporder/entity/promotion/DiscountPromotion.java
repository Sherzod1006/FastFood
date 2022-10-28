package uz.pdp.apporder.entity.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.template.AbsIntegerEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountPromotion extends AbsIntegerEntity {

    @Column(nullable = false)
    private Float moreThan;

    @Column(nullable = false)
    private Float discount;

    public DiscountPromotion(Integer id,Float moreThan, Float discount) {
        super(id);
        this.moreThan = moreThan;
        this.discount = discount;
    }
}
