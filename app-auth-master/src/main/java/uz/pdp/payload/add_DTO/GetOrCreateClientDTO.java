package uz.pdp.payload.add_DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.entity.Client;
import uz.pdp.entity.User;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class GetOrCreateClientDTO {

    @NotNull
    private String phoneNumber;

    private String name;

    private Long birthDate;


    public User mappingUser() {
        return new User(phoneNumber);
    }

    public Client mappingClient(User user) {
        return new Client(user,birthDate,name);
    }

}
