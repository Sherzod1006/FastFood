package uz.pdp.apporder.projection;

import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.entity.enums.PaymentType;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface StatisticsOrderDTOProjection {
    Integer getBranchId();

    Long getOrderId();

    String getClientId();

    String getOperatorId();

    PaymentType getPaymentType();

    OrderStatusEnum getStatusEnum();

    LocalDateTime getOrderedAt();

}
