package uz.pdp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CurrierDTO {

    @NotNull
    private Long birthDate;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String carNumber;

    private String driverLicense;

    @NotBlank
    private String phoneNumber;
}
