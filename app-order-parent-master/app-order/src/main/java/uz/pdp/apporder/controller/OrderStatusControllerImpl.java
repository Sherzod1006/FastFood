package uz.pdp.apporder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderDTO;
import uz.pdp.apporder.service.OrderStatusService;

@RestController
@RequiredArgsConstructor
public class OrderStatusControllerImpl implements OrderStatusController {

    private final OrderStatusService orderStatusService;

    @Override
    public ApiResult<OrderDTO> transferAccepted(Long id) {
        return orderStatusService.transferAcceptedStatus(id);
    }

    @Override
    public ApiResult<OrderDTO> transferCooking(Long id) {
        return orderStatusService.transferCookingStatus(id);
    }

    @Override
    public ApiResult<OrderDTO> transferReady(Long id) {
        return orderStatusService.transferReadyStatus(id);
    }

    @Override
    public ApiResult<OrderDTO> transferSent(Long id) {
        return orderStatusService.transferSentStatus(id);
    }

    @Override
    public ApiResult<OrderDTO> transferFinished(Long id) {
        return orderStatusService.transferFinishedStatus(id);
    }

    @Override
    public ApiResult<OrderDTO> transferRejected(Long id) {
        return orderStatusService.transferRejectedStatus(id);
    }
}
