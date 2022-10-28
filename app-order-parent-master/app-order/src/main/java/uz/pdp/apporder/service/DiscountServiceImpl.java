package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.Discount;
import uz.pdp.apporder.exceptions.RestException;
import uz.pdp.apporder.payload.AddDiscountDTO;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.DiscountDTO;
import uz.pdp.apporder.repository.DiscountRepository;
import uz.pdp.appproduct.entity.Product;
import uz.pdp.appproduct.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;


    @Override
    public ApiResult<DiscountDTO> add(AddDiscountDTO addDiscountDTO) {
        Product product = productRepository.findById(addDiscountDTO.getProductId()).orElseThrow();

        Discount discount = new Discount(null,
                product,
                addDiscountDTO.getDiscount(),
                addDiscountDTO.getStartDate(),
                addDiscountDTO.getEndDate());

        Discount save = discountRepository.save(discount);

        return ApiResult.successResponse(mapDiscountToDiscountDTO(save));
    }

    @Override
    public ApiResult<DiscountDTO> edit(DiscountDTO discountDTO) {

        if (!discountRepository.existsById(discountDTO.getId()))
            throw RestException.restThrow("discount not found", HttpStatus.NOT_FOUND);

        Product product = productRepository.findById(discountDTO.getProductId()).orElseThrow(() ->
                RestException.restThrow("product not fount", HttpStatus.NOT_FOUND)
        );

        Discount discount = mapDiscountDTOToDiscount(discountDTO, product);

        discountRepository.save(discount);

        return ApiResult.successResponse(discountDTO);
    }

    @Override
    public ApiResult<Boolean> end(Integer id) {
        Discount discount = discountRepository.findById(id).orElseThrow(() ->
                RestException.restThrow("discount not found", HttpStatus.NOT_FOUND)
        );

        discount.setEndDate(new Date().getTime());

        discountRepository.save(discount);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<DiscountDTO> get(Integer id) {
        Discount discount = discountRepository.findById(id).orElseThrow(() ->
                RestException.restThrow("discount not found", HttpStatus.NOT_FOUND)
        );
        return ApiResult.successResponse(mapDiscountToDiscountDTO(discount));
    }

    @Override
    public ApiResult<List<DiscountDTO>> getDiscounts() {

        List<Discount> all = discountRepository.findAll();

        return ApiResult.successResponse(mapDiscountsToDiscountDTOs(all));
    }


    /**
     * This method is used for getting current active discounts for product
     *
     * @param productId to get discounts for this product
     * @return DiscountDTO if exists  returns null if there isn't active discount
     */
    @Override
    public ApiResult<DiscountDTO> getActiveDiscountForProduct(Integer productId) {
        if (!productRepository.existsById(productId))
            throw RestException.restThrow("product not fount", HttpStatus.NOT_FOUND);

        Optional<Discount> optionalDiscount = discountRepository
                .findByProductIdAndEndDateIsAfter(
                        productId,
                        System.currentTimeMillis());
        if (optionalDiscount.isEmpty())
            return null;

        return ApiResult.successResponse(mapDiscountToDiscountDTO(optionalDiscount.get()));

    }

    @Override
    public ApiResult<List<DiscountDTO>> getActiveDiscounts() {
        List<Discount> discountList = discountRepository.findAllByEndDateIsAfter(System.currentTimeMillis());

        return ApiResult.successResponse(mapDiscountsToDiscountDTOs(discountList));
    }

    @Override
    public Optional<Float> getDiscountsSumOfProducts(List<Integer> collect) {
        return discountRepository.getOverallSumOfProductsDiscount(collect, System.currentTimeMillis());
    }


    private DiscountDTO mapDiscountToDiscountDTO(Discount discount) {
        return new DiscountDTO(discount.getId(),
                discount.getProduct().getId(),
                discount.getDiscount(),
                discount.getStartDate(),
                discount.getEndDate()
        );
    }

    private Discount mapDiscountDTOToDiscount(DiscountDTO discountDTO, Product product) {
        return Discount.builder()
                .id(discountDTO.getId())
                .product(product)
                .discount(discountDTO.getDiscount())
                .startDate(discountDTO.getStartDate())
                .endDate(discountDTO.getEndDate())
                .build();
    }

    private List<DiscountDTO> mapDiscountsToDiscountDTOs(List<Discount> discounts) {
        List<DiscountDTO> discountDTOS = new ArrayList<>();
        for (Discount discount : discounts)
            discountDTOS.add(mapDiscountToDiscountDTO(discount));
        return discountDTOS;
    }

}