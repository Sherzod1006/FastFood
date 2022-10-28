package uz.pdp.appproduct.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.CategoryDTO;
import uz.pdp.appproduct.dto.ViewDTO;
import uz.pdp.appproduct.util.RestConstants;

import java.util.List;

@RequestMapping(CategoryController.BASE_PATH)
public interface CategoryController {
    String BASE_PATH = RestConstants.SERVICE_BASE_PATH + "category";


    @PostMapping("/add")
    ApiResult<CategoryDTO> add(@RequestBody CategoryDTO categoryDTO);

    @PostMapping(path = "/list")
    ApiResult<List<CategoryDTO>> getCategories(@RequestBody(required = false) ViewDTO viewDTO);

    @GetMapping("/{id}")
    ApiResult<CategoryDTO> get(@PathVariable Integer id);


    @PutMapping("/{id}")
    ApiResult<CategoryDTO> edit(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO);

    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable Integer id);
}
