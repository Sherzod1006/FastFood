package uz.pdp.apporder.payload.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private Integer id;

    private Float price;

    private String name;

    private Integer categoryId;

    private boolean active;

    private String description;
}