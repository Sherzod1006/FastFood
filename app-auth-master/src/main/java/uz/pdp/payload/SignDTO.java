package uz.pdp.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Getter
@AllArgsConstructor
public class SignDTO implements Serializable {

    @NotBlank(message = "{MUST_NOT_BE_BLANK_PHONE_NUMBER}")
    private String phoneNumber;

    private Long birthDate;

    private String name;
}
