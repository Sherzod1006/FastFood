package uz.pdp.appproduct.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.ProductDTO;
import uz.pdp.appproduct.dto.ViewDTO;
import uz.pdp.appproduct.entity.Category;
import uz.pdp.appproduct.entity.Product;
import uz.pdp.appproduct.exceptions.RestException;
import uz.pdp.appproduct.repository.CategoryRepository;
import uz.pdp.appproduct.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ApiResult<ProductDTO> addProduct(ProductDTO productDTO) {

        if (productRepository.existsByNameAndCategoryId(
                productDTO.getName(),
                productDTO.getCategoryId()))
            throw RestException
                    .restThrow("this product already exists",
                            HttpStatus.CONFLICT);

        Category category = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(() -> RestException.restThrow("Category doesn't exist", HttpStatus.NOT_FOUND));

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setCategory(category);
        product.setActive(productDTO.isActive());

        productRepository.save(product);

        return ApiResult.successResponse(mapProductToProductDTO(product));
    }

    //todo viewDto orqali olsih qoldi, page ham
    @Override
    public ApiResult<List<ProductDTO>> getProductsForAdmin(ViewDTO viewDTO, int page, int size) {
        List<Product> all = productRepository.findAll();
        return ApiResult.successResponse(getDTOListFromEntity(all));
    }

    //done
    @Override
    public ApiResult<ProductDTO> getProductForAdmin(Integer id) {

        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> RestException.restThrow("id is not available", HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(mapProductToProductDTO(product));
    }

    //done
    @Override
    public ApiResult<?> deleteProduct(Integer id) {
        if (productRepository.existsById(id)) {
            throw RestException.restThrow("id is not available", HttpStatus.NOT_FOUND);
        }

        productRepository.deleteById(id);
        return ApiResult.successResponse("deleted successfully");
    }

    //done
    @Override
    public ApiResult<ProductDTO> editProduct(Integer id, ProductDTO productDTO) {

        Product product = productRepository.
                findById(id).
                orElseThrow(() -> RestException.
                        restThrow("this product does not exists", HttpStatus.NOT_FOUND));

        Category category = categoryRepository.
                findById(productDTO.getCategoryId())
                .orElseThrow(() ->
                        RestException.restThrow("category is not available", HttpStatus.NOT_FOUND));

        if (productRepository.existsByNameAndCategoryIdAndIdNot(productDTO.getName(), productDTO.getCategoryId(), id))
            throw RestException.restThrow("this product is already exists", HttpStatus.CONFLICT);


        product.setName(productDTO.getName());
        product.setActive(productDTO.isActive());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setCategory(category);

        productRepository.save(product);

        Product edited = productRepository.findById(id).orElseThrow();

        return ApiResult.successResponse(mapProductToProductDTO(edited));
    }

    //done
    @Override
    public ApiResult<ProductDTO> getProduct(Integer id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(
                        () -> RestException.restThrow("id is not available", HttpStatus.NOT_FOUND));


        if (product.isActive())
            throw RestException.restThrow("this product is not active", HttpStatus.LOCKED);

        return ApiResult.successResponse(mapProductToProductDTO(product));
    }

    //done
    @Override
    public ApiResult<List<ProductDTO>> getProducts() {
        List<Product> all = productRepository.findAll();

        return ApiResult.successResponse(getDTOListFromEntity(all));
    }

    private List<ProductDTO> getDTOListFromEntity(List<Product> products) {
        return products
                .stream()
                .map(this::mapProductToProductDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO mapProductToProductDTO(Product product) {

        return new ProductDTO(product.getPrice(),
                product.getName(),
                product.getCategory().getId(),
                product.isActive(),
                product.getDescription());
    }

}
