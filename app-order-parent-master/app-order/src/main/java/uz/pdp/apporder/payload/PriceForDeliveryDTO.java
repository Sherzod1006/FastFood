package uz.pdp.apporder.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.Branch;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceForDeliveryDTO {

    private Branch branch;

    private Float priceForPerKilometre;

    private Float initialPrice;

    private Integer initialDistance;
}
