package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.entity.enums.PermissionEnum;
import uz.pdp.payload.add_DTO.AddRoleDTO;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.RoleDTO;
import uz.pdp.service.RoleServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoleControllerImpl implements RoleController {

    private final RoleServiceImpl roleService;

    @Override
    public ApiResult<RoleDTO> add(@Valid @RequestBody AddRoleDTO addRoleDTO) {
        return roleService.add(addRoleDTO);
    }

    @Override
    public ApiResult<Boolean> edit(AddRoleDTO addRoleDTO, Integer id) {
        return roleService.edit(addRoleDTO, id);
    }

    @Override
    public ApiResult<Boolean> delete(@PathVariable Integer id) {
        return roleService.delete(id);
    }

    @Override
    public ApiResult<RoleDTO> getRole(Integer id) {
        return roleService.getRole(id);
    }

    @Override
    public ApiResult<List<RoleDTO>> getRoles() {
        return roleService.getRoles();
    }

    @Override
    public ApiResult<PermissionEnum[]> getPermissions() {
        return roleService.getPermissions();
    }
}
