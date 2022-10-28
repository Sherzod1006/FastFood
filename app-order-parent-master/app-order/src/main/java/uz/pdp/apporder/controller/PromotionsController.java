package uz.pdp.apporder.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.apporder.payload.promotion.AddPromotionDTO;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.DiscountDTO;
import uz.pdp.apporder.payload.promotion.PromotionDTO;
import uz.pdp.appproduct.util.RestConstants;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping(PromotionsController.BASE_PATH)
public interface PromotionsController {

    String BASE_PATH = RestConstants.SERVICE_BASE_PATH + "discount";

    String ADD_PATH = "/add";

    String EDIT_PATH = "/{id}";

    String STOP_PATH = "/stop/{id}";

    String GET_PATH = "/{id}";

    String GET_ALL_PATH = "/list";
    String GET_ALL_BY_STATUS_PATH = "/get-promotion";

    @PostMapping(value = ADD_PATH)
    ApiResult<DiscountDTO> add(@Valid @RequestBody AddPromotionDTO addPromotionDTO);

    @PutMapping(value = EDIT_PATH)
    ApiResult<DiscountDTO> edit(@Valid @RequestBody PromotionDTO promotionDTO);

    @GetMapping(value = STOP_PATH)
    ApiResult<Boolean> end(@NotNull Long id);

    @GetMapping(value = GET_PATH)
    ApiResult<PromotionDTO> get(@NotNull Long id);

    @GetMapping(GET_ALL_PATH)
    ApiResult<List<PromotionDTO>> getPromotions();

    @PostMapping(value = GET_ALL_BY_STATUS_PATH)
    ApiResult<List<PromotionDTO>> getPromotions(@Valid @RequestBody Boolean isActive);

}
