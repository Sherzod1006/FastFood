package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.apporder.entity.promotion.*;
import uz.pdp.apporder.exceptions.RestException;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.promotion.*;
import uz.pdp.apporder.payload.promotion.template.PromotionDTOType;
import uz.pdp.apporder.repository.*;
import uz.pdp.appproduct.entity.Category;
import uz.pdp.appproduct.entity.Product;
import uz.pdp.appproduct.repository.CategoryRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionsServiceImpl implements PromotionsService {

    private final DiscountPromotionRepository discountPromotionRepository;

    private final PromotionRepository promotionRepository;

    private final DeliveryPromotionRepository deliveryPromotionRepository;

    private final ProductPromotionRepository productPromotionRepository;

    private final CategoryRepository categoryRepository;

    private final BonusProductPromotionRepository bonusProductPromotionRepository;


    private <T extends PromotionDTOType> Promotion checkPromotion(T otherPromotion) {
        Promotion promotion = new Promotion();

        byte notNullCount = 0;

        checkPromotionDate(otherPromotion.getStartDate(), otherPromotion.getEndDate());

        if (Objects.nonNull(promotion.getDiscountPromotion())) {
            notNullCount++;
            promotion.setDiscountPromotion(checkPromotion(otherPromotion.getDiscountPromotion()));
        }

        if (Objects.nonNull(otherPromotion.getDeliveryPromotion()) && notNullCount++ == 0)
            promotion.setDeliveryPromotion(checkDelivery(otherPromotion.getDeliveryPromotion()));

        if (Objects.nonNull(otherPromotion.getProductPromotion()) && notNullCount++ == 0)
            promotion.setProductPromotion(checkProductPromotion(otherPromotion.getProductPromotion()));

        if (Objects.nonNull(otherPromotion.getBonusProductPromotion()) && notNullCount++ == 0)
            promotion.setBonusProductPromotion(checkBonusProduct(otherPromotion.getBonusProductPromotion()));

        if (notNullCount == 0)
            throw RestException.restThrow("don't have promotion. Only 1 promotion is required!", HttpStatus.BAD_REQUEST);

        if (notNullCount > 1)
            throw RestException.restThrow("There are "+notNullCount + " promotions. Only 1 promotion is required!", HttpStatus.BAD_REQUEST);

        return promotion;
    }

    @Override
    @Transactional
    public ApiResult<PromotionDTO> add(AddPromotionDTO addPromotionDTO) {

        Promotion promotion = checkPromotion(addPromotionDTO);

        promotion.setStartDate(addPromotionDTO.getStartDate());

        promotion.setEndDate(addPromotionDTO.getEndDate());

        Promotion save = promotionRepository.save(promotion);

        return ApiResult.successResponse(promotionToPromotionDTO(save));
    }

    @Override
    public ApiResult<PromotionDTO> edit(PromotionDTO promotionDTO) {

        if (!promotionRepository.existsById(promotionDTO.getId()))
            throw RestException.restThrow("promotion not found", HttpStatus.NOT_FOUND);

        Promotion promotion = checkPromotion(promotionDTO);

        checkExistsPromotionAndSave(promotion);

        Promotion save = promotionRepository.save(promotion);

        return ApiResult.successResponse(promotionToPromotionDTO(save));
    }

    private void checkExistsPromotionAndSave(Promotion promotion) {

        if (promotion.getProductPromotion() != null &&
                promotionRepository.existsByIdAndProductPromotionId(promotion.getId(), promotion.getProductPromotion().getId()))
            productPromotionRepository.save(promotion.getProductPromotion());

        else if (promotion.getDeliveryPromotion() != null &&
                promotionRepository.existsByIdAndDeliveryPromotionId(promotion.getId(), promotion.getDeliveryPromotion().getId()))
            deliveryPromotionRepository.save(promotion.getDeliveryPromotion());

        else if (promotion.getDiscountPromotion() != null &&
                promotionRepository.existsByIdAndDiscountPromotionId(promotion.getId(), promotion.getDiscountPromotion().getId()))
            discountPromotionRepository.save(promotion.getDiscountPromotion());

        else if (promotion.getBonusProductPromotion() != null &&
                promotionRepository.existsByIdAndBonusProductPromotionId(promotion.getId(), promotion.getBonusProductPromotion().getId()))
            bonusProductPromotionRepository.save(promotion.getBonusProductPromotion());

        else
            throw RestException.restThrow("product promotion not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiResult<Boolean> stopPromotion(Long id) {

        Promotion promotion = promotionRepository.findById(id).orElseThrow(() ->
                RestException.restThrow("Promotion not found", HttpStatus.NOT_FOUND)
        );

        long time = new Date().getTime();

        if (promotion.getEndDate() < new Date().getTime())
            throw RestException.restThrow("promotion already finished", HttpStatus.BAD_REQUEST);

        promotion.setEndDate(time);

        promotionRepository.save(promotion);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<PromotionDTO> getPromotion(Long id) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow(() ->
                RestException.restThrow("promotion not found", HttpStatus.NOT_FOUND)
        );

        return ApiResult.successResponse(promotionToPromotionDTO(promotion));
    }

    @Override
    public ApiResult<List<PromotionDTO>> getPromotions() {
        return ApiResult.successResponse(
                promotionsToPromotionDTOs(
                        promotionRepository.findAll()
                )
        );
    }

    @Override
    public ApiResult<List<PromotionDTO>> getPromotions(Boolean isActive) {
        long time = new Date().getTime();
        if (isActive)
            return ApiResult.successResponse(
                    promotionRepository
                            .getActivePromotion(time)
                            .stream()
                            .map(this::promotionToPromotionDTO)
                            .collect(Collectors.toList())
            );

        return ApiResult.successResponse(
                promotionRepository
                        .getNotActivePromotion(time)
                        .stream()
                        .map(this::promotionToPromotionDTO)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<Promotion> get1ActivePromotion() {
        return promotionRepository.get1ActivePromotion(System.currentTimeMillis());
    }


    private DiscountPromotion checkPromotion(DiscountPromotionDTO dpd) {

        if (dpd.getDiscount() == null || dpd.getMoreThan() == null)
            throw RestException.restThrow("discount promotion has problem", HttpStatus.BAD_REQUEST);

        return dpd.toDiscountPromotion(dpd);
    }

    private DeliveryPromotion checkDelivery(DeliveryPromotionDTO dpd) {

        if (dpd.getMoreThan() == null)
            throw RestException.restThrow("delivery promotion more than must be not null",
                    HttpStatus.BAD_REQUEST);
        checkPDeliveryTime(dpd.getStartTime(), dpd.getEndTime());

        return dpd.toDeliveryPromotion(dpd);
    }

    private ProductPromotion checkProductPromotion(ProductPromotionDTO promotionDTO) {

        if (Objects.nonNull(promotionDTO.getMoreThan()))
            throw RestException.restThrow(" product promotion has problem. more than field must be not null",
                    HttpStatus.BAD_REQUEST);

        if (Objects.nonNull(promotionDTO.getBonusProducts()) || promotionDTO.getBonusProducts().isEmpty())
            throw RestException.restThrow("bonus must be not empty", HttpStatus.BAD_REQUEST);

        return productPromotionDTOToProductPromotion(promotionDTO);
    }

    private BonusProductPromotion checkBonusProduct(BonusProductPromotionDTO bonus) {
        if (Objects.isNull(bonus.getProductDTO()))
            throw RestException.restThrow("bonus product must be not null", HttpStatus.BAD_REQUEST);

        if (bonus.getBonusCount() <= 0)
            throw RestException.restThrow("bonus count must grate 0", HttpStatus.BAD_REQUEST);

        if (bonus.getMoreThan() <= 0)
            throw RestException.restThrow("bonus sum must grate 0", HttpStatus.BAD_REQUEST);

        return mapBonusProductPromotionDTOToBonusProductPromotion(bonus);
    }

    private void checkPromotionDate(Long startDate, Long endDate) {
        if (startDate >= endDate)
            throw RestException.restThrow("the start time is greater than the end time", HttpStatus.BAD_REQUEST);

        if (endDate < new Date().getTime())
            throw RestException.restThrow("the end time is greater than the now", HttpStatus.BAD_REQUEST);
    }

    private void checkPDeliveryTime(Long startTime, Long endTime) {

        if (startTime == null)
            if (endTime != null)
                if (endTime > 24 || endTime < 1)
                    throw RestException.restThrow("delivery end time invalid",
                            HttpStatus.BAD_REQUEST);

        if (endTime == null)
            if (startTime != null)
                if (startTime > 24 || startTime < 0)
                    throw RestException.restThrow("delivery start time invalid",
                            HttpStatus.BAD_REQUEST);

        // all ok if not throw
    }

    //todo MAPPING PART

    @Override
    public PromotionDTO promotionToPromotionDTO(Promotion promotion) {
        return new PromotionDTO(
                promotion.getId(),
                promotion.getStartDate(),
                promotion.getEndDate(),
                new DeliveryPromotionDTO(
                        promotion.getDeliveryPromotion().getId(),
                        promotion.getDeliveryPromotion().getMoreThan(),
                        promotion.getDeliveryPromotion().getStartTime(),
                        promotion.getDeliveryPromotion().getEndTime()
                ),
                new ProductPromotionDTO(
                        promotion.getProductPromotion().getId(),
                        promotion.getProductPromotion().getMoreThan(),
                        getDTOListFromEntity(promotion.getProductPromotion().getBonusProducts()),
                        promotion.getProductPromotion().isCanAllBeTaken()
                ),
                new DiscountPromotionDTO(
                        promotion.getDiscountPromotion().getId(),
                        promotion.getDiscountPromotion().getMoreThan(),
                        promotion.getDiscountPromotion().getDiscount()
                ),
                new BonusProductPromotionDTO(
                        promotion.getBonusProductPromotion().getId(),
                        promotion.getBonusProductPromotion().getMoreThan(),
                        promotion.getBonusProductPromotion().getBonusCount(),
                        mapProductToProductDTO(promotion.getBonusProductPromotion().getProduct())
                )
        );
    }

    private List<PromotionDTO> promotionsToPromotionDTOs(List<Promotion> promotions) {
        List<PromotionDTO> promotionDTOS = new ArrayList<>();

        for (Promotion promotion : promotions)
            promotionDTOS.add(promotionToPromotionDTO(promotion));

        return promotionDTOS;
    }

    private List<ProductDTO> getDTOListFromEntity(List<Product> products) {
        return products
                .stream()
                .map(this::mapProductToProductDTO)
                .collect(Collectors.toList());
    }

    private List<Product> mapProductDTOToProduct(List<ProductDTO> products) {
        return products
                .stream()
                .map(this::mapProductDTOToProduct)
                .collect(Collectors.toList());
    }

    private ProductDTO mapProductToProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getPrice(),
                product.getName(),
                product.getCategory().getId(),
                product.isActive(),
                product.getDescription());
    }

    private Product mapProductDTOToProduct(ProductDTO product) {
        Category category_not_found = categoryRepository.findById(product.getCategoryId()).orElseThrow(() ->
                RestException.restThrow("category not found", HttpStatus.NOT_FOUND)
        );

        return new Product(
                product.getId(),
                product.isActive(),
                product.getPrice(),
                product.getName(),
                category_not_found,
                product.getDescription()
        );
    }

    private ProductPromotion productPromotionDTOToProductPromotion(ProductPromotionDTO promotionDTO) {

        return new ProductPromotion(
                promotionDTO.getId(),
                promotionDTO.getMoreThan(),
                mapProductDTOToProduct(promotionDTO.getBonusProducts()),
                promotionDTO.isCanAllBeTaken()
        );
    }

    private BonusProductPromotion mapBonusProductPromotionDTOToBonusProductPromotion(BonusProductPromotionDTO bonus) {
        return new BonusProductPromotion(
                bonus.getId(),
                bonus.getMoreThan(),
                bonus.getBonusCount(),
                mapProductDTOToProduct(bonus.getProductDTO())
        );
    }
}