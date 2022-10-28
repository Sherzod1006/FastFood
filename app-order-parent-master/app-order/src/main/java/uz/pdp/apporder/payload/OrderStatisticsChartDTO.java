package uz.pdp.apporder.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.Map;

@Getter
@NoArgsConstructor
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderStatisticsChartDTO {

    private Map<?,Integer> intervalData;

    private Integer branchId;

    private LocalDate fromDate;

    private LocalDate tillDate;

    private LocalDate currentDate;

    private Double paymentPayme;

    private Double paymentClick;

    private Double paymentChash;

    private Double paymentTerminal;

    private Double totalPayment;

    @Enumerated(value = EnumType.STRING)
    private OrderStatusEnum orderStatusEnum;


    public OrderStatisticsChartDTO(double paymentChash,
                                   double paymentPayme, double paymentClick,
                                   double paymentTerminal, double totalPayment) {
        this.paymentPayme = paymentPayme;
        this.paymentClick = paymentClick;
        this.paymentChash = paymentChash;
        this.paymentTerminal = paymentTerminal;
        this.totalPayment = totalPayment;
    }

    public OrderStatisticsChartDTO(Map<?, Integer> intervalData,
                                   Integer branchId,
                                   LocalDate fromDate,
                                   LocalDate tillDate,
                                   OrderStatusEnum orderStatusEnum) {
        this.intervalData = intervalData;
        this.branchId = branchId;
        this.fromDate = fromDate;
        this.tillDate = tillDate;
        this.orderStatusEnum = orderStatusEnum;
    }
}
