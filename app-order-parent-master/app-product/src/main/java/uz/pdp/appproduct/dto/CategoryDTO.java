package uz.pdp.appproduct.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is error and success returning class from api
 */

@Setter
@Getter
public class CategoryDTO {

    private Integer id;

    private String nameUz;

    private String nameRu;

    private CategoryDTO parent;

    private Integer parentId;
}
