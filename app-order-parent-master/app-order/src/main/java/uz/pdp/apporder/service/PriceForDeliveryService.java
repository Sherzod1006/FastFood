package uz.pdp.apporder.service;

import uz.pdp.apporder.entity.PriceForDelivery;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.PriceForDeliveryDTO;

import java.util.List;

public interface PriceForDeliveryService {
    ApiResult<PriceForDelivery> add(PriceForDeliveryDTO priceForDeliveryDTO);

    ApiResult<PriceForDelivery> getPriceForDeliveryById(Integer id);

    ApiResult<List<PriceForDelivery>> getAllPricesForDeliveryList();

    ApiResult<Boolean> deletePriceForDeliveryById(Integer id);

    ApiResult<Boolean> delete();

    ApiResult<PriceForDelivery> edit(Integer id, PriceForDeliveryDTO priceForDeliveryDTO);
}
