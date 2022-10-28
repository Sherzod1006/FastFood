package uz.pdp.apporder.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.apporder.payload.*;
import uz.pdp.apporder.payload.promotion.AddPromotionDTO;
import uz.pdp.apporder.payload.promotion.PromotionDTO;
import uz.pdp.appproduct.util.RestConstants;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping(value = PromotionController.BASE_PATH)
public interface PromotionController {
    String BASE_PATH = RestConstants.SERVICE_BASE_PATH + "promotion";

    String ADD_PATH = "/add";
    String EDIT_PATH = "/{id}";
    String STOP_PATH = "/stop/{id}";
    String GET_PATH = "/{id}";
    String GET_ACTIVE_PROMOTION_PATH = "/active-promotions";
    String GET_NOT_ACTIVE_PROMOTION_PATH = "/non-active-promotions";
    String GET_ALL_PATH = "/list";


    @PostMapping(value = ADD_PATH)
    ApiResult<PromotionDTO> add(@RequestBody @Valid AddPromotionDTO promotionDTO);

    @PutMapping(value = EDIT_PATH)
    ApiResult<PromotionDTO> edit(@RequestBody @Valid PromotionDTO promotionDTO);

    @PostMapping(value = STOP_PATH)
    ApiResult<Boolean> end(@PathVariable @NotNull Long id);

    @GetMapping(value = GET_PATH)
    ApiResult<PromotionDTO> get(@PathVariable @NotNull Long id);


    @GetMapping(value = GET_ALL_PATH)
    ApiResult<List<PromotionDTO>> getPromotions();

    @GetMapping(value = GET_ACTIVE_PROMOTION_PATH)
    ApiResult<List<PromotionDTO>> getPromotions(Boolean isActive);

}
