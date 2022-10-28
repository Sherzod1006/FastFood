package uz.pdp.apporder.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTO {

    @NotNull
    private Integer id;

    @NotNull
    private Integer productId;

    @NotNull
    private Float discount;

    @NotNull
    private Long startDate;

    @NotNull
    private Long endDate;

}
