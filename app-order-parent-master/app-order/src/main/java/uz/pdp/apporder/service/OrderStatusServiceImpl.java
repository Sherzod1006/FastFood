package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.Order;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.exceptions.RestException;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderDTO;
import uz.pdp.apporder.repository.OrderRepository;
import uz.pdp.appproduct.aop.AuthFeign;
import uz.pdp.appproduct.dto.ClientDTO;
import uz.pdp.appproduct.dto.EmployeeDTO;
import uz.pdp.appproduct.util.CommonUtils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {

    private final OrderRepository orderRepository;

    private final AuthFeign authFeign;

    @Override
    public ApiResult<OrderDTO> transferAcceptedStatus(Long id) {
        Order order = orderRepository.getByIdAndStatusEnumOrStatusEnum(
                id,
                OrderStatusEnum.NEW,
                OrderStatusEnum.PAYMENT_WAITING
        ).orElseThrow(() ->
                RestException.restThrow("order not found", HttpStatus.NOT_FOUND)
        );

        order.setStatusEnum(OrderStatusEnum.ACCEPTED);
        order.setAcceptedAt(LocalDateTime.now());

        orderRepository.save(order);

        return ApiResult.successResponse(toOrderDTO(order));
    }

    @Override
    public ApiResult<OrderDTO> transferCookingStatus(Long id) {

        Order order = getOrder(id, OrderStatusEnum.ACCEPTED);

        order.setStatusEnum(OrderStatusEnum.COOKING);
        order.setCookingAt(LocalDateTime.now());

        orderRepository.save(order);

        return ApiResult.successResponse(toOrderDTO(order));
    }

    @Override
    public ApiResult<OrderDTO> transferReadyStatus(Long id) {

        Order order = getOrder(id, OrderStatusEnum.COOKING);

        order.setStatusEnum(OrderStatusEnum.READY);
        order.setReadyAt(LocalDateTime.now());
        orderRepository.save(order);

        return ApiResult.successResponse(toOrderDTO(order));
    }

    @Override
    public ApiResult<OrderDTO> transferSentStatus(Long id) {

        Order order = getOrder(id, OrderStatusEnum.READY);

        order.setStatusEnum(OrderStatusEnum.SENT);
        order.setSentAt(LocalDateTime.now());

        orderRepository.save(order);
        return ApiResult.successResponse(toOrderDTO(order));
    }

    @Override
    public ApiResult<OrderDTO> transferFinishedStatus(Long id) {
        Order order = getOrder(id, OrderStatusEnum.SENT);

        order.setStatusEnum(OrderStatusEnum.FINISHED);
        order.setClosedAt(LocalDateTime.now());

        orderRepository.save(order);
        return ApiResult.successResponse(toOrderDTO(order));
    }

    @Override
    public ApiResult<OrderDTO> transferRejectedStatus(Long id) {
        Order order = orderRepository.getOrderIdAndStatus(
                id,
                OrderStatusEnum.PAYMENT_WAITING,
                OrderStatusEnum.NEW,
                OrderStatusEnum.SENT
        ).orElseThrow(() ->
                RestException.restThrow("Order not found", HttpStatus.BAD_REQUEST)
        );

        order.setStatusEnum(OrderStatusEnum.REJECTED);
        order.setCancelledAt(LocalDateTime.now());

        orderRepository.save(order);
        return ApiResult.successResponse(toOrderDTO(order));
    }

    private Order getOrder(Long id, OrderStatusEnum statusEnum) {
        return orderRepository.getByIdAndStatusEnum(
                id,
                statusEnum).orElseThrow(() ->
                RestException.restThrow("order not found", HttpStatus.NOT_FOUND)
        );
    }

    private OrderDTO toOrderDTO(Order order) {
        return OrderDTO.builder()
                .branchName(order.getBranch().getName())
                .clientDTO(getClientDTO(order.getClientId()))
                .number(order.getNumber())
                .paymentType(order.getPaymentType())
                .build();
    }

    private EmployeeDTO getEmployeeDTO(UUID uuid) {
        uz.pdp.appproduct.dto.ApiResult<EmployeeDTO> employeeDTO = authFeign.getEmployeeDTO(uuid, getToken());

        if (Objects.isNull(employeeDTO.getData())||
                Objects.isNull(employeeDTO.getData().getPermissions()))
            throw RestException.restThrow("not access", HttpStatus.UNAUTHORIZED);

        return employeeDTO.getData();
    }

    private ClientDTO getClientDTO(UUID uuid) {

        ClientDTO data = authFeign.getClientDTO(uuid, getToken()).getData();
        if (data == null)
            throw RestException.restThrow("user not found", HttpStatus.NOT_FOUND);
        return data;
    }

    private String getToken() {
        String authorization = CommonUtils.getCurrentRequest().getHeader("Authorization");
        if (authorization == null)
            throw RestException.restThrow("Not access", HttpStatus.UNAUTHORIZED);
        return authorization;
    }
}