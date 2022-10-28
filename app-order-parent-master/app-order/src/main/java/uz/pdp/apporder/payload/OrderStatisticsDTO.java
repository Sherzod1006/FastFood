package uz.pdp.apporder.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.entity.enums.PaymentType;
import uz.pdp.appproduct.dto.ClientDTO;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatisticsDTO {

    private BranchDTO branchDTO;

    private ClientDTO clientDTO;

    private OperatorDTOForList operatorDTOForList;

    private OrderStatusEnum statusEnum;

    private PaymentType paymentType;

    private Double sum;

    private LocalDateTime orderedAt;

}
