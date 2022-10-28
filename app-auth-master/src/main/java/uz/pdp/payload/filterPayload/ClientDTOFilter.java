package uz.pdp.payload.filterPayload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ClientDTOFilter {

    private UUID id;
    private String name;
    private String phoneNumber;
    private Integer orderCount;
    private Boolean enabled;
}
