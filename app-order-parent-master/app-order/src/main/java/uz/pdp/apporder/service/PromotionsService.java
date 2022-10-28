package uz.pdp.apporder.service;

import uz.pdp.apporder.entity.promotion.Promotion;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.promotion.AddPromotionDTO;
import uz.pdp.apporder.payload.promotion.PromotionDTO;

import java.util.List;
import java.util.Optional;

public interface PromotionsService {

    ApiResult<PromotionDTO> add(AddPromotionDTO addPromotionDTO);

    ApiResult<PromotionDTO> edit(PromotionDTO promotionDTO);

    ApiResult<Boolean> stopPromotion(Long id);

    ApiResult<PromotionDTO> getPromotion(Long id);

    ApiResult<List<PromotionDTO>> getPromotions();

    ApiResult<List<PromotionDTO>> getPromotions(Boolean isActive);

    Optional<Promotion> get1ActivePromotion();

    PromotionDTO promotionToPromotionDTO(Promotion promotion);
}
