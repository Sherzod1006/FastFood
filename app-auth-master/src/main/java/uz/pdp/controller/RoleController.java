package uz.pdp.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.enums.PermissionEnum;
import uz.pdp.payload.add_DTO.AddRoleDTO;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.RoleDTO;
import uz.pdp.util.RestConstants;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping(path = RoleController.ROLE_BASE_PATH)
public interface RoleController {
    String ROLE_BASE_PATH = RestConstants.SERVICE_BASE_PATH + "role";

    @PostMapping
    ApiResult<RoleDTO> add(@Valid @RequestBody AddRoleDTO addRoleDTO);

    @PutMapping("/{id}")
    ApiResult<Boolean> edit(@Valid AddRoleDTO addRoleDTO, @PathVariable @NotNull Integer id);

    @DeleteMapping("/{id}")
    ApiResult<Boolean> delete(@NotNull @PathVariable Integer id);

    @GetMapping("/{id}")
    ApiResult<RoleDTO> getRole(@NotNull @PathVariable Integer id);

    @GetMapping("/list")
    ApiResult<List<RoleDTO>> getRoles();

    @GetMapping("permissions-for-role")
    ApiResult<PermissionEnum[]> getPermissions();

}
