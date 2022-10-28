package uz.pdp.apporder.service;

import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderStatisticsDTO;
import uz.pdp.apporder.payload.ViewDTO;

import java.util.List;

public interface OrderStatisticsInList {

    ApiResult<List<OrderStatisticsDTO>> getStatisticsForList(ViewDTO viewDTO, int page, int size);

}
