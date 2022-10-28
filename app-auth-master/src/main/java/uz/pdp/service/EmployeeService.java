package uz.pdp.service;

import uz.pdp.payload.add_DTO.AddEmployeeDTO;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.response_DTO.EmployeeDTO;


import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    ApiResult<List<EmployeeDTO>> getAll();

    ApiResult<EmployeeDTO> get(UUID id);

    ApiResult<String> add(AddEmployeeDTO employee);

    ApiResult<Boolean> edit(AddEmployeeDTO addEmployeeDTO, UUID id);

    ApiResult<String> delete(UUID id);

}
