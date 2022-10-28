package uz.pdp.apporder.payload.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPromotionDTO {

    private Integer id;

    private Float moreThan;

    private List<ProductDTO> bonusProducts;

    private boolean canAllBeTaken;

}
