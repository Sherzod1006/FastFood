package uz.pdp.appproduct.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.appproduct.dto.*;
import uz.pdp.appproduct.entity.Category;
import uz.pdp.appproduct.exceptions.RestException;
import uz.pdp.appproduct.repository.CategoryRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@DynamicUpdate
@DynamicInsert
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;


    /**
     * Add new category
     * <p>
     * An exception to type {@code java.lang.NullPointerException}
     * parameter if null
     *
     * @param categoryDTO being added category object
     * @return {@link CategoryDTO}
     * @throws NullPointerException if categoryDTO is null
     */
    @Override
    public ApiResult<CategoryDTO> add(CategoryDTO categoryDTO) {

        if (categoryRepository.existsByNameUzIgnoreCase(categoryDTO.getNameUz()))
            throw RestException.restThrow("ALREADY_EXISTS", HttpStatus.BAD_REQUEST);

        if (categoryRepository.existsByNameRuIgnoreCase(categoryDTO.getNameRu()))
            throw RestException.restThrow("ALREADY_EXISTS", HttpStatus.BAD_REQUEST);


        Category category = getCategoryFromCategoryDTO(categoryDTO);

        categoryRepository.save(category);

        return ApiResult.successResponse("SUCCESSFULLY_SAVED",
                getCategoryDTOFromCategory(category)
        );
    }

    @Override
    public ApiResult<List<CategoryDTO>> getCategories(ViewDTO viewDTO) {

        List<Category> categoriesAfterSearching = searchingCategory(viewDTO);

        List<CategoryDTO> categoryDTOListAfterSort = sortCategories(viewDTO, categoriesAfterSearching);

        return ApiResult.successResponse("SUCCESSFULLY_SEARCHED-SORTED", categoryDTOListAfterSort);
    }

    @Override
    public ApiResult<CategoryDTO> get(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                RestException.restThrow("This id category not found", HttpStatus.NOT_FOUND)
        );

        CategoryDTO categoryDTO = getCategoryDTOFromCategory(category);

        return ApiResult.successResponse(categoryDTO);
    }

    @Override
    public ApiResult<CategoryDTO> edit(CategoryDTO categoryDTO, Integer id) {
        if (categoryRepository.existsByNameUzIgnoreCaseAndIdNot(categoryDTO.getNameUz(), id))
            throw RestException.restThrow("ALREADY_EXISTS", HttpStatus.ALREADY_REPORTED);


        if (categoryRepository.existsByNameRuIgnoreCaseAndIdNot(categoryDTO.getNameRu(), id))
            throw RestException.restThrow("ALREADY_EXISTS", HttpStatus.ALREADY_REPORTED);


        Category category = categoryRepository.findById(id).orElseThrow(() ->
                RestException.restThrow("Category not founded in this id", HttpStatus.NOT_FOUND)
        );

        Category parent = null;
        if (Objects.nonNull(categoryDTO.getParentId()))
            parent = categoryRepository
                    .findById(categoryDTO.getParentId())
                    .orElseThrow(() -> RestException
                            .restThrow("", HttpStatus.BAD_REQUEST));

        category.setParentCategory(parent);
        category.setNameUz(categoryDTO.getNameUz());
        category.setNameRu(categoryDTO.getNameRu());

        categoryRepository.save(category);

        return get(id);
    }

    @Override
    public ApiResult<?> delete(Integer id) {
        categoryRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("CATEGORY_NOT_FOUND", HttpStatus.NOT_FOUND)
        );
        categoryRepository.deleteById(id);
        return ApiResult.successResponse();
    }

    private Category getCategoryFromCategoryDTO(CategoryDTO categoryDTO) {
        Category category = new Category();

        Category parent = null;
        if (Objects.nonNull(categoryDTO.getParentId()))
            parent = categoryRepository
                    .findById(categoryDTO.getParentId())
                    .orElseThrow(() -> RestException
                            .restThrow("", HttpStatus.BAD_REQUEST));

        category.setNameUz(categoryDTO.getNameUz());
        category.setNameRu(categoryDTO.getNameRu());
        category.setParentCategory(parent);
        return category;
    }

    private CategoryDTO getCategoryDTOFromCategory(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId(category.getId());
        if (Objects.nonNull(category.getParentCategory()))
            categoryDTO.setParent(getCategoryDTOFromCategory(category.getParentCategory()));
        categoryDTO.setNameRu(category.getNameRu());
        categoryDTO.setNameUz(category.getNameUz());

        return categoryDTO;
    }

    private List<Integer> mapLanguageToCategoryIds(List<Category> categoryList) {
        List<Integer> categoryIds = new ArrayList<>();
        for (Category language : categoryList) {
            categoryIds.add(language.getId());
        }
        return categoryIds;
    }

    private List<Category> searchingCategory(ViewDTO viewDTO) {
        List<Category> categories = categoryRepository.findAll();

        if (Objects.nonNull(viewDTO) && Objects.nonNull(viewDTO.getSearching())) {
            for (String column : viewDTO.getSearching().getColumns()) {
                List<Integer> idList = mapLanguageToCategoryIds(categories);
                switch (column) {
                    case "nameUz":
                        categories = categoryRepository.findAllByNameUzContainingIgnoreCaseAndIdIn(viewDTO.getSearching().getValue(), idList);
                        break;
                    case "nameRu":
                        categories = categoryRepository.findAllByNameRuContainingIgnoreCaseAndIdIn(viewDTO.getSearching().getValue(), idList);
                        break;
                }
            }
        }
        return categories;
    }

    private List<CategoryDTO> sortCategories(ViewDTO viewDTO, List<Category> categoryList) {

        if (Objects.nonNull(viewDTO) && Objects.nonNull(viewDTO.getSorting())) {
            for (SortingDTO sortingDTO : viewDTO.getSorting()) {
                List<Integer> idList = mapLanguageToCategoryIds(categoryList);
                switch (sortingDTO.getType()) {
                    case ASC:
                        switch (sortingDTO.getName()) {
                            case "nameUz":
                                categoryList = categoryRepository.findAllByIdInAndNameUzOrderByNameUzAsc(idList, sortingDTO.getName());
                                break;
                            case "nameRU":
                                categoryList = categoryRepository.findAllByIdInAndNameRuOrderByNameRuAsc(idList, sortingDTO.getName());
                                break;
                        }
                        break;
                    case DESC:
                        switch (sortingDTO.getName()) {
                            case "nameUz":
                                categoryList = categoryRepository.findAllByIdInAndNameUzOrderByNameUzDesc(idList, sortingDTO.getName());
                                break;
                            case "nameRU":
                                categoryList = categoryRepository.findAllByIdInAndNameRuOrderByNameRuDesc(idList, sortingDTO.getName());
                                break;
                        }
                        break;
                }
            }
        }
        return categoryList
                .stream()
                .map(this::getCategoryDTOFromCategory)
                .collect(Collectors.toList());
    }
}
