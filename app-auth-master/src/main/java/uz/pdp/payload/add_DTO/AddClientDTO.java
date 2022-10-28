package uz.pdp.payload.add_DTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;
@Data
public class AddClientDTO {

    private String phoneNumber;

    private String name;

    private Long birthDate;
}
