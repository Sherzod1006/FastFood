package uz.pdp.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Getter
@AllArgsConstructor
public class SignInForEmployeeDTO implements Serializable {

    @NotBlank(message = "{MUST_NOT_BE_BLANK_PHONE_NUMBER}")
    private String phoneNumber;

    @NotBlank(message = "{MUST_NOT_BE_BLANK_VERIFICATION_CODE}")
    private String password;

}
