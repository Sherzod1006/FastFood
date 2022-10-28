package uz.pdp.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private String searchingField;
    private String employeeType;
    private Integer size;
    private Integer page;
}
