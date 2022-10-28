package uz.pdp.apporder.payload;

import lombok.Getter;
import uz.pdp.apporder.payload.enums.SortingTypeEnum;

@Getter
public class SortingDTO {

    private String name;

    private SortingTypeEnum type;
}
