package uz.pdp.apporder.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import uz.pdp.apporder.entity.enums.PaymentType;
import uz.pdp.appproduct.dto.ClientDTO;
import uz.pdp.appproduct.dto.CurrierDTO;
import uz.pdp.appproduct.dto.EmployeeDTO;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private Long id;

    private ClientDTO clientDTO;

    private EmployeeDTO operatorDTO;

    private PaymentType paymentType;

    private Float deliverySum;

    private Float productsSum;

    private Float discountSum;

    private Integer number;

    private String branchName;

    private LocalDateTime orderedAt;

    private LocalDateTime orderedAtByStatus;

    private CurrierDTO currierDTO;
}