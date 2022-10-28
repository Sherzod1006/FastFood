package uz.pdp.apporder.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.entity.enums.PaymentType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderListDTO {

    private String branchName;

    private PaymentType paymentType;

    private OrderStatusEnum orderStatusEnum;

}
