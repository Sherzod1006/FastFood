package uz.pdp.appproduct.service;

import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.ProductDTO;
import uz.pdp.appproduct.dto.ViewDTO;

import java.util.List;

public interface ProductService {
    ApiResult<ProductDTO> addProduct(ProductDTO productDTO);

    ApiResult<List<ProductDTO>> getProductsForAdmin(ViewDTO viewDTO,
                                                    int page,
                                                    int size);


    ApiResult<ProductDTO> getProductForAdmin(Integer id);

    ApiResult<?> deleteProduct(Integer id);

    ApiResult<ProductDTO> editProduct(Integer id, ProductDTO productDTO);


    ApiResult<ProductDTO> getProduct(Integer id);

    ApiResult<List<ProductDTO>> getProducts();

}