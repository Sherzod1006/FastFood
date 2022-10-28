package uz.pdp.apporder.entity.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.template.AbsIntegerEntity;
import uz.pdp.appproduct.entity.Product;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BonusProductPromotion extends AbsIntegerEntity {

    @Column(nullable = false)
    private Float moreThan;

    @Column(nullable = false)
    private Short bonusCount;

    @ManyToOne
    private Product product;

    public BonusProductPromotion(Integer id, Float moreThan, Short bonusCount, Product product) {
        super(id);
        this.moreThan = moreThan;
        this.bonusCount = bonusCount;
        this.product = product;
    }
}
