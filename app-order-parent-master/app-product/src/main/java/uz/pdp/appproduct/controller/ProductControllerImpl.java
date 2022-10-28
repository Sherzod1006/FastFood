package uz.pdp.appproduct.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appproduct.aop.CheckAuthEmpl;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.ProductDTO;
import uz.pdp.appproduct.dto.ViewDTO;
import uz.pdp.appproduct.dto.enums.PermissionEnum;
import uz.pdp.appproduct.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    @CheckAuthEmpl(permissions = {PermissionEnum.ADD_PRODUCT})
    @Override
    public ApiResult<ProductDTO> addProduct(ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @Override
    public ApiResult<List<ProductDTO>> getProductsForAdmin(ViewDTO viewDTO, int page, int size) {
        return getProductsForAdmin(viewDTO, page, size);
    }

    @Override
    public ApiResult<ProductDTO> getProductForAdmin(Integer id) {
        return productService.getProductForAdmin(id);
    }

    @Override
    public ApiResult<?> deleteProduct(Integer id) {
        return productService.deleteProduct(id);
    }

    @CheckAuthEmpl(permissions = {PermissionEnum.EDIT_PRODUCT})
    @Override
    public ApiResult<ProductDTO> editProduct(Integer id, ProductDTO productDTO) {
        return productService.editProduct(id, productDTO);
    }

    @Override
    public ApiResult<ProductDTO> getProduct(Integer id) {
        return productService.getProduct(id);
    }

    @Override
    public ApiResult<List<ProductDTO>> getProducts() {
        return productService.getProducts();
    }
}
