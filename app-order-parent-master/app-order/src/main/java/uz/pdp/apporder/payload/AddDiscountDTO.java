package uz.pdp.apporder.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddDiscountDTO {

    @NotNull
    private Integer productId;

    @NotNull
    private Float discount;

    @NotNull
    private Long startDate;

    @NotNull
    private Long endDate;

}
