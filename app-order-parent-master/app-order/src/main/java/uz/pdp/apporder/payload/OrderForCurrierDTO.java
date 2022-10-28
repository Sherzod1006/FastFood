package uz.pdp.apporder.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.enums.PaymentType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderForCurrierDTO {

    private LocalDate orderedDate;

    private String branchName;

    private Integer orderNumber;

    private String clientAddress;

    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;

    private Float productsSum;

    private Float deliverySum;






}
