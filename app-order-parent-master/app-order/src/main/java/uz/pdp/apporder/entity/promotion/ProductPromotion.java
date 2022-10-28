package uz.pdp.apporder.entity.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.template.AbsIntegerEntity;
import uz.pdp.appproduct.entity.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPromotion extends AbsIntegerEntity {

    @Column(nullable = false)
    private Float moreThan;

    @ManyToMany
    private List<Product> bonusProducts;

    @Column(nullable = false)
    private boolean canAllBeTaken;


    public ProductPromotion(Integer id, Float moreThan, List<Product> bonusProducts, boolean canAllBeTaken) {
        super(id);
        this.moreThan = moreThan;
        this.bonusProducts = bonusProducts;
        this.canAllBeTaken = canAllBeTaken;
    }
}