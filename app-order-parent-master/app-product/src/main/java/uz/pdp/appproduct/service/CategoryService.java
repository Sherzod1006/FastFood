package uz.pdp.appproduct.service;

import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.CategoryDTO;
import uz.pdp.appproduct.dto.ViewDTO;
import java.util.List;

public interface CategoryService {
    ApiResult<CategoryDTO> add(CategoryDTO categoryDTO);

    ApiResult<List<CategoryDTO>> getCategories(ViewDTO viewDTO);

    ApiResult<CategoryDTO> get(Integer id);

    ApiResult<CategoryDTO> edit(CategoryDTO categoryDTO, Integer id);

    ApiResult<?> delete(Integer id);
}
