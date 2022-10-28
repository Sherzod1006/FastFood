package uz.pdp.appproduct.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appproduct.aop.CheckAuthEmpl;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.CategoryDTO;
import uz.pdp.appproduct.dto.ViewDTO;
import uz.pdp.appproduct.dto.enums.PermissionEnum;
import uz.pdp.appproduct.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryControllerImpl implements CategoryController {
    
    private final CategoryService categoryService;

    @CheckAuthEmpl(permissions = {PermissionEnum.ADD_CATEGORY})
    @Override
    public ApiResult<CategoryDTO> add(CategoryDTO categoryDTO) {
        return categoryService.add(categoryDTO);
    }

    @Override
    public ApiResult<List<CategoryDTO>> getCategories(@RequestBody(required = false) ViewDTO viewDTO) {
        return categoryService.getCategories(viewDTO);
    }

    @Override
    public  ApiResult<CategoryDTO>get(Integer id) {
        return categoryService.get(id);
    }

    @Override
    public ApiResult<CategoryDTO> edit(Integer id, CategoryDTO categoryDTO) {
        return categoryService.edit(categoryDTO,id);
    }

    @Override
    public ApiResult<?> delete(Integer id) {
        return categoryService.delete(id);
    }
}
