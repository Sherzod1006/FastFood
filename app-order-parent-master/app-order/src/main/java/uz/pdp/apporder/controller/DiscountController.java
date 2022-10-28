package uz.pdp.apporder.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.apporder.payload.AddDiscountDTO;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.DiscountDTO;
import uz.pdp.appproduct.util.RestConstants;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping(value = DiscountController.BASE_PATH)
public interface DiscountController {
    String BASE_PATH = RestConstants.SERVICE_BASE_PATH + "discount";

    String ADD_PATH = "/add";
    String EDIT_PATH = "/{id}";
    String STOP_PATH = "/stop/{id}";
    String GET_PATH = "/{id}";
    String GET_DISCOUNT_FOR_PRODUCT_PATH = "/{productId}";
    String GET_ALL_ACTIVE_DISCOUNTS_PATH = "/active-discounts";
    String GET_ALL_PATH = "/list";

    @PostMapping(value = ADD_PATH)
    ApiResult<DiscountDTO> add(@RequestBody @Valid AddDiscountDTO addDiscountDTO);

    @PutMapping(value = EDIT_PATH)
    ApiResult<DiscountDTO> edit(@RequestBody @Valid DiscountDTO discountDTO);

    @PostMapping(value = STOP_PATH)
    ApiResult<Boolean> end(@PathVariable @NotNull Integer id);

    @GetMapping(value = GET_PATH)
    ApiResult<DiscountDTO> get(@PathVariable @NotNull Integer id);

    @GetMapping(value = GET_DISCOUNT_FOR_PRODUCT_PATH)
    ApiResult<DiscountDTO> getActiveDiscountForProduct(@PathVariable @NotNull Integer productId);

    @GetMapping(value = GET_ALL_PATH)
    ApiResult<List<DiscountDTO>> getDiscounts();

    @GetMapping(value = GET_ALL_ACTIVE_DISCOUNTS_PATH)
    ApiResult<List<DiscountDTO>> getActiveDiscounts();


}
