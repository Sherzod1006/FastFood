package uz.pdp.appproduct.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ErrorData {


    private String msg;

    private Integer code;

    private String fieldName;

    public ErrorData(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }
}
