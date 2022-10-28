package uz.pdp.apporder.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.enums.PaymentType;
import uz.pdp.appproduct.dto.ClientDTO;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderWebDTO {

    @NotNull
    private ClientDTO client;

    @NotNull
    private Set<OrderProductDTO> products;

    @NotNull
    private AddressDTO address;

    @NotNull
    private PaymentType paymentType;


    public Map<Integer, OrderProductDTO> orderProductDTOMap() {
        if (Objects.isNull(products))
            return new HashMap<>();

        return products
                .stream()
                .collect(Collectors.toMap(
                        OrderProductDTO::getProductId,
                        o -> o));
    }

}
