package uz.pdp.apporder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apporder.payload.promotion.AddPromotionDTO;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.promotion.PromotionDTO;
import uz.pdp.apporder.service.PromotionsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PromotionControllerImpl implements PromotionController {

    private final PromotionsService promotionsService;

    @Override
    public ApiResult<PromotionDTO> add(AddPromotionDTO addPromotionDTO) {
        return promotionsService.add(addPromotionDTO);
    }

    @Override
    public ApiResult<PromotionDTO> edit(PromotionDTO promotionDTO) {
        return promotionsService.edit(promotionDTO);
    }

    @Override
    public ApiResult<Boolean> end(Long id) {
        return promotionsService.stopPromotion(id);
    }

    @Override
    public ApiResult<PromotionDTO> get(Long id) {
        return promotionsService.getPromotion(id);
    }

    @Override
    public ApiResult<List<PromotionDTO>> getPromotions() {
        return promotionsService.getPromotions();
    }

    @Override
    public ApiResult<List<PromotionDTO>> getPromotions(Boolean isActive) {
        return promotionsService.getPromotions(isActive);
    }
}
