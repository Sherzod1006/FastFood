package uz.pdp.apporder.payload.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.payload.OrderDTO;

/**
 * Me: muhammadqodir
 * Project: app-order-parent/IntelliJ IDEA
 * Date:Sun 09/10/22 23:02
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderWithPromotionDTO {

    private OrderDTO order;
    private PromotionDTO promotion;

}
