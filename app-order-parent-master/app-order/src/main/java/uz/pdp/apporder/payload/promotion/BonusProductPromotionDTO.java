package uz.pdp.apporder.payload.promotion;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BonusProductPromotionDTO {

    private Integer id;

    private Float moreThan;

    private Short bonusCount;

    private ProductDTO productDTO;
}
