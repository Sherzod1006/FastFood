package uz.pdp.apporder.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.enums.PaymentType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUserDTO {

    @NotNull
    @NotEmpty
    private Set<OrderProductDTO> orderProductsDTOList;

    @NotNull
    private AddressDTO addressDTO;

    @NotNull
    private PaymentType paymentType;

}
