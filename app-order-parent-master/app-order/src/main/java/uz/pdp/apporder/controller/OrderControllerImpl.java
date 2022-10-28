package uz.pdp.apporder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apporder.payload.promotion.AcceptPromotionDTO;
import uz.pdp.apporder.payload.promotion.OrderWithPromotionDTO;
import uz.pdp.appproduct.aop.CheckAuth;
import uz.pdp.appproduct.aop.CheckAuthEmpl;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.appproduct.dto.enums.PermissionEnum;
import uz.pdp.apporder.payload.*;
import uz.pdp.apporder.service.OrderService;
import uz.pdp.apporder.service.OrderServiceChart;
import uz.pdp.apporder.service.OrderStatisticsInList;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {


    private final OrderService orderService;

    private final OrderServiceChart orderServiceChart;

    private final OrderStatisticsInList orderStatisticsInList;

    @CheckAuth
    @Override
    public ApiResult<?> saveOrderFromApp(OrderUserDTO order) {
        return orderService.saveOrder(order);
    }

    @Override
    public ApiResult<?> saveOrderFromWeb(OrderWebDTO order) {
        return orderService.saveOrder(order);
    }

    @Override
    public ApiResult<OrderWithPromotionDTO> getOrderPromotions(Long orderId) {
        return orderService.getOrderPromotions(orderId);
    }

    @Override
    public ApiResult<OrderDTO> getOrderPromotions(AcceptPromotionDTO acceptPromotionDTO) {
        return orderService.acceptOrderPromotion(acceptPromotionDTO);
    }


    @Override
    @CheckAuthEmpl(permissions = {PermissionEnum.SHOW_STATISTICS})
    public ApiResult<OrderStatisticsChartDTO> showStatisticsOrder(OrderChartDTO orderChartDTO) {
        return orderServiceChart.getStatisticsOrder(orderChartDTO);
    }

    @Override
    public ApiResult<List<OrderStatisticsDTO>> showStatisticsOrderInList(ViewDTO viewDTO, Integer page, Integer size) {
        return orderStatisticsInList.getStatisticsForList(viewDTO, page, size);
    }

    @Override
    @CheckAuthEmpl(permissions = {PermissionEnum.SHOW_STATISTICS})
    public ApiResult<OrderStatisticsChartDTO> showStatisticsPayment(OrderChartPaymentDTO orderChartPaymentDTO) {
        return orderServiceChart.getStatisticsPayment(orderChartPaymentDTO);
    }


    @Override
    @CheckAuthEmpl(permissions = {PermissionEnum.ADD_ORDER})
    public ApiResult<List<OrderDTO>> getOrdersByStatus(String orderStatus) {
        return orderService.getOrdersByStatus(orderStatus);
    }

    @Override
    @CheckAuth(permissions = {PermissionEnum.GET_ORDER_FOR_COURIER})
    public ApiResult<?> getOrderForCourier(OrderStatusEnum orderStatusEnum) {
        return orderService.getOrderForCourier(orderStatusEnum);
    }

    @Override
    public ApiResult<OrderDTO> getOneOrder(Long id) {
        return orderService.getOneOrder(id);
    }

    @Override
    public ApiResult<List<OrderDTO>> getOrderList() {
        return orderService.getOrders();
    }

    @Override
    public ApiResult<OrderStatusWithCountAndPrice> getOrderStatusCountPrice(OrderStatusEnum orderStatus) {
        return orderService.getOrderStatusCountPrice(orderStatus);
    }

    @Override
    public ApiResult<?> editOrder(OrderWebDTO order, Long id) {
        return orderService.editOrder(order, id);
    }

    @Override
    public ApiResult<List<OrderForCurrierDTO>> getAllOrdersForCurrier(UUID id) {
        return orderService.getAllOrdersForCurrier(id);
    }

    @Override
    public ApiResult<List<OrderForCurrierDTO>> getOrdersForCurrierByOrderedDate(UUID id, LocalDateTime localDate) {
        return orderService.getOrdersForCurrierByOrderedDate(id, localDate);
    }

}
