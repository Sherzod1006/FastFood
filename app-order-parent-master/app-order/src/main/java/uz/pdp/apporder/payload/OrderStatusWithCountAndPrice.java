package uz.pdp.apporder.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusWithCountAndPrice {

    private OrderStatusEnum statusEnum;

    private Integer count;

    private Double price;

}
