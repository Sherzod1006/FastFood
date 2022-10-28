package uz.pdp.apporder.service;

import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderChartDTO;
import uz.pdp.apporder.payload.OrderChartPaymentDTO;
import uz.pdp.apporder.payload.OrderStatisticsChartDTO;

public interface OrderServiceChart {
    ApiResult<OrderStatisticsChartDTO> getStatisticsPayment(OrderChartPaymentDTO orderChartPaymentDTO);

    ApiResult<OrderStatisticsChartDTO> getStatisticsOrder(OrderChartDTO orderChartDTO);
}
