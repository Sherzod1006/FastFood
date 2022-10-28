package uz.pdp.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.entity.Role;
import uz.pdp.entity.enums.PermissionEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class RoleDTO {

    Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @NotEmpty
    private Set<PermissionEnum> permissions;

    private Set<PageDTO> pages;

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.description = role.getDescription();
        this.permissions = role.getPermissions();
    }

}
