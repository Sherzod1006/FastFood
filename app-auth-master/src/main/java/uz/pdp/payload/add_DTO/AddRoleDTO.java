package uz.pdp.payload.add_DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.entity.Role;
import uz.pdp.entity.enums.PermissionEnum;
import uz.pdp.entity.enums.RoleTypeEnum;
import uz.pdp.payload.AddPageDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class AddRoleDTO {


    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @NotEmpty
    private Set<PermissionEnum> permissions;

    private Set<AddPageDTO> pages;

    public Role mapToRole() {
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        role.setPermissions(permissions);
        role.setRoleType(RoleTypeEnum.OTHER);
        return role;
    }

}
