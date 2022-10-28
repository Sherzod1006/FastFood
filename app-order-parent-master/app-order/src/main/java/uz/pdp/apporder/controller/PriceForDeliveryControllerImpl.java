package uz.pdp.apporder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apporder.entity.PriceForDelivery;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.PriceForDeliveryDTO;
import uz.pdp.apporder.service.PriceForDeliveryServiceImpl;
import uz.pdp.appproduct.aop.CheckAuthEmpl;
import uz.pdp.appproduct.dto.enums.PermissionEnum;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PriceForDeliveryControllerImpl implements PriceForDeliveryController{
    private final PriceForDeliveryServiceImpl priceForDeliveryService;

    @Override
    @CheckAuthEmpl(permissions = {PermissionEnum.ADD_PRICE_FOR_DELIVERY})
    public ApiResult<PriceForDelivery> add(PriceForDeliveryDTO priceForDeliveryDTO) {
        return priceForDeliveryService.add(priceForDeliveryDTO);
    }

    @Override
    @CheckAuthEmpl(permissions = {PermissionEnum.GET_ALL_PRICES_FOR_DELIVERIES})
    public ApiResult<List<PriceForDelivery>> pricesForDeliveries() {
        return priceForDeliveryService.getAllPricesForDeliveryList();
    }

    @Override
    @CheckAuthEmpl(permissions = {PermissionEnum.GET_PRICE_FOR_DELIVERY})
    public ApiResult<PriceForDelivery> priceForDelivery(Integer id) {
        return priceForDeliveryService.getPriceForDeliveryById(id);
    }

    @Override
    @CheckAuthEmpl(permissions = {PermissionEnum.DELETE_PRICE_FOR_DELIVERY})
    public ApiResult<Boolean> deletePriceForDelivery(Integer id) {
        return priceForDeliveryService.deletePriceForDeliveryById(id);
    }

    @Override
    @CheckAuthEmpl(permissions = {PermissionEnum.DELETE_ALL_PRICES_FOR_DELIVERIES})
    public ApiResult<Boolean> delete() {
        return priceForDeliveryService.delete();
    }

    @Override
    @CheckAuthEmpl(permissions = {PermissionEnum.EDIT_PRICE_FOR_DELIVERY})
    public ApiResult<PriceForDelivery> edit(Integer id, PriceForDeliveryDTO priceForDeliveryDTO) {
        return priceForDeliveryService.edit(id, priceForDeliveryDTO);
    }


}
