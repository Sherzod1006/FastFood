package uz.pdp.apporder.payload.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Me: muhammadqodir
 * Project: app-order-parent/IntelliJ IDEA
 * Date:Sun 09/10/22 23:14
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class AcceptPromotionDTO {

    private Long promotionId;
    private Long orderId;
    private boolean isProductPromotion;
    private Integer chosenProductId;
}
