package uz.pdp.payload.response_DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class EmployeeDTO {

    private UUID id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Integer roleId;
}
