package uz.pdp.payload.filterPayload;

import lombok.Getter;
import uz.pdp.payload.filterPayload.enums.SortColumnEnum;
import uz.pdp.payload.filterPayload.enums.SortTypeEnum;

@Getter
public class SortingDTO {

    private SortColumnEnum columnName;

    private SortTypeEnum type;
}
