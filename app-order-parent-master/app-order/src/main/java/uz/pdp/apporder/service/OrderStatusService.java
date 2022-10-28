package uz.pdp.apporder.service;

import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderDTO;

public interface OrderStatusService {

    ApiResult<OrderDTO> transferAcceptedStatus(Long id);

    ApiResult<OrderDTO> transferCookingStatus(Long id);

    ApiResult<OrderDTO> transferReadyStatus(Long id);

    ApiResult<OrderDTO> transferSentStatus(Long id);

    ApiResult<OrderDTO> transferFinishedStatus(Long id);

    ApiResult<OrderDTO> transferRejectedStatus(Long id);

}
