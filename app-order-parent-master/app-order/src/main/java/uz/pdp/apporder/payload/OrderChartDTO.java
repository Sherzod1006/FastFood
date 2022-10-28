package uz.pdp.apporder.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderChartDTO {

    private Integer branchId;

    @NotNull
    private LocalDate fromDate;

    @NotNull
    private LocalDate tillDate;

    @Enumerated(value = EnumType.STRING)
    private OrderStatusEnum orderStatusEnum;

}
