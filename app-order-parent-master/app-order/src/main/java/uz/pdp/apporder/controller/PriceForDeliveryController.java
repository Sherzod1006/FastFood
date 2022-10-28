package uz.pdp.apporder.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.apporder.entity.PriceForDelivery;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.PriceForDeliveryDTO;
import uz.pdp.appproduct.aop.CheckAuthEmpl;
import uz.pdp.appproduct.dto.enums.PermissionEnum;
import uz.pdp.appproduct.util.RestConstants;

import java.util.List;

@RequestMapping(value = PriceForDeliveryController.BASE_PATH)
public interface PriceForDeliveryController {

    String BASE_PATH = RestConstants.SERVICE_BASE_PATH + "price-for-delivery";

    String ADD_PATH = "/add";
//    String EDIT_PATH = "/edit/{id}";

    String GET_ALL_PATH = "/list";
    String GET_BY_ID_PATH ="/{id}";
    String DELETE_BY_ID_PATH = "/{id}";
    String DELETE_ALL_PATH = "/delete-all";


    @PostMapping(PriceForDeliveryController.ADD_PATH)
    ApiResult<PriceForDelivery> add(@RequestBody PriceForDeliveryDTO priceForDeliveryDTO);

    @GetMapping(value = GET_ALL_PATH)
    ApiResult<List<PriceForDelivery>> pricesForDeliveries();

    @GetMapping(value = GET_BY_ID_PATH)
    ApiResult<PriceForDelivery> priceForDelivery(@PathVariable Integer id);

    @DeleteMapping(value = DELETE_BY_ID_PATH)
    ApiResult<Boolean> deletePriceForDelivery(@PathVariable Integer id);

    @DeleteMapping(value = DELETE_ALL_PATH)
    ApiResult<Boolean> delete();


    @PutMapping("/edit/{id}")
    ApiResult<PriceForDelivery> edit(@PathVariable Integer id, @RequestBody PriceForDeliveryDTO priceForDeliveryDTO);

}
