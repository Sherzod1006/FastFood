package uz.pdp.payload.add_DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddEmployeeDTO {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Integer roleId;
}