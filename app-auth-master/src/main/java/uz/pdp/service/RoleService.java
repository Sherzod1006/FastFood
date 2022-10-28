package uz.pdp.service;

import uz.pdp.entity.enums.PermissionEnum;
import uz.pdp.payload.add_DTO.AddRoleDTO;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.RoleDTO;

import java.util.List;

public interface RoleService {

    ApiResult<RoleDTO> add(AddRoleDTO addRoleDTO);

    ApiResult<Boolean> delete(Integer id);

    ApiResult<List<RoleDTO>> getRoles();

    ApiResult<PermissionEnum[]> getPermissions();

    ApiResult<Boolean> edit(AddRoleDTO addRoleDTO, Integer id);

    ApiResult<RoleDTO> getRole(Integer id);
}
