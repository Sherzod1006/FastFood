package uz.pdp.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.add_DTO.GetOrCreateClientDTO;
import uz.pdp.payload.response_DTO.ClientDTO;
import uz.pdp.payload.response_DTO.EmployeeDTO;
import uz.pdp.payload.response_DTO.UserDTO;
import uz.pdp.util.RestConstants;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping(path = UserController.PROJECT_HELPER_CONTROLLER_BASE_PATH)
public interface UserController {
    String PROJECT_HELPER_CONTROLLER_BASE_PATH = RestConstants.SERVICE_BASE_PATH + "user";
    String GET_EMPLOYEE_WITH_UUID = "/employee/{id}";
    String USER_ME_PATH = "/me";
    String GET_CLIENT_BY_USERID = "/by-id/{userId}";
    //todo
    String GET_OR_CREATE_CLIENT_BY_PHONE_NUMBER = "/get-or-create-client";

    @GetMapping(value = GET_EMPLOYEE_WITH_UUID)
    ApiResult<EmployeeDTO> getEmployeeWithId(@PathVariable UUID id);

    @GetMapping(value = USER_ME_PATH)
    ApiResult<UserDTO> getUserByToken();

    @GetMapping(value = GET_CLIENT_BY_USERID)
    ApiResult<ClientDTO> getClientByUserId(@PathVariable UUID userId);

    @GetMapping(value = GET_OR_CREATE_CLIENT_BY_PHONE_NUMBER)
    ApiResult<ClientDTO> getClientByPhoneNumber(@RequestBody @Valid GetOrCreateClientDTO getOrCreateClientDTO);

}
