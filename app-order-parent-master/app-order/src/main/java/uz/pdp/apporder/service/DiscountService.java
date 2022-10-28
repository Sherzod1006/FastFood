package uz.pdp.apporder.service;

import uz.pdp.apporder.payload.AddDiscountDTO;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.DiscountDTO;

import java.util.List;
import java.util.Optional;

public interface DiscountService {
     ApiResult<DiscountDTO> add(AddDiscountDTO addDiscountDTO);

    ApiResult<DiscountDTO> edit(DiscountDTO discountDTO);

    ApiResult<Boolean> end(Integer id);

    ApiResult<DiscountDTO> get(Integer id);

    ApiResult<List<DiscountDTO>> getDiscounts();

    ApiResult<DiscountDTO> getActiveDiscountForProduct(Integer productId);

    ApiResult<List<DiscountDTO>> getActiveDiscounts();

    Optional<Float> getDiscountsSumOfProducts(List<Integer> collect);
}
