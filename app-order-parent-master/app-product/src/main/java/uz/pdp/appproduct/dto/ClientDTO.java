package uz.pdp.appproduct.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ClientDTO {

    private UUID id;

    private String name;

    private String phoneNumber;

    public UUID getUserId() {
        return id;
    }
}
