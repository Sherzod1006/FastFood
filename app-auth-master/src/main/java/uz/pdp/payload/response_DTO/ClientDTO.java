package uz.pdp.payload.response_DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import uz.pdp.entity.Client;
import uz.pdp.entity.User;

import java.util.UUID;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {

    private UUID id;

    private String name;

    private Long birthDate;

    private UUID userId;

    private String phoneNumber;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

    public static ClientDTO mapping(Client client) {
        return new ClientDTO(client);
    }

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.birthDate = client.getBirthDate();
        setUserFields(client.getUser());
    }

    void setUserFields(User user) {
        if (user == null)
            return;
        this.userId = user.getId();
        this.phoneNumber = user.getPhoneNumber();
        this.accountNonExpired = user.isAccountNonExpired();
        this.accountNonLocked = user.isAccountNonLocked();
        this.credentialsNonExpired = user.isCredentialsNonExpired();
        this.enabled = user.isEnabled();
    }

    public void setToClient(Client client) {
        client.setName(name);
        client.setBirthDate(birthDate);
        User user = client.getUser();
        if (user != null)
            user.setId(userId);
    }

}
