package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.payload.add_DTO.AddEmployeeDTO;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.response_DTO.EmployeeDTO;
import uz.pdp.service.EmployeeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EmployeeControllerImpl implements EmployeeController{

    private final EmployeeService employeeService;

    @Override
    public ApiResult<List<EmployeeDTO>> getAll() {
        return employeeService.getAll();
    }

    @Override
    public ApiResult<EmployeeDTO> get(UUID id) {
        return employeeService.get(id);
    }

    @Override
    public ApiResult<String> add(AddEmployeeDTO employee) {
        return employeeService.add(employee);
    }

    @Override
    public ApiResult<Boolean> edit(AddEmployeeDTO addEmployeeDTO, UUID id) {
        return employeeService.edit(addEmployeeDTO, id);
    }

    @Override
    public ApiResult<String> delete(UUID id) {
        return employeeService.delete(id);
    }
}
