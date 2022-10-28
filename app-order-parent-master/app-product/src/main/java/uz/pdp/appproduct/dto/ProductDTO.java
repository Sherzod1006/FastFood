package uz.pdp.appproduct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private Float price;

    private String name;

    private Integer categoryId;

    private boolean active;

    private String description;
}