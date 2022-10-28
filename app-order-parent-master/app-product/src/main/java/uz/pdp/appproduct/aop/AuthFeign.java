package uz.pdp.appproduct.aop;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.ClientDTO;
import uz.pdp.appproduct.dto.CurrierDTO;
import uz.pdp.appproduct.dto.EmployeeDTO;
import uz.pdp.appproduct.util.RestConstants;

import java.util.UUID;

@FeignClient(value = AuthFeign.BASE_PATH)
public interface AuthFeign {

    String BASE_PATH = "AUTH-SERVICE";
    String GET_EMPLOYEE_BY_TOKEN = "/api/auth/v1/user/me";
    String GET_CLIENT_BY_ID = "/api/auth/v1/user/by-id/{userId}";

    String VERIFICATION_BY_PHONE = "/verification-phone-number/{phoneNumber}";

    @RequestMapping(method = RequestMethod.GET, value = GET_EMPLOYEE_BY_TOKEN, consumes = "application/json")
    ApiResult<ClientDTO> getAuthorizedClientDTO(@RequestHeader(RestConstants.AUTHORIZATION_HEADER) String token);

    @RequestMapping(method = RequestMethod.GET, value = GET_EMPLOYEE_BY_TOKEN, consumes = "application/json")
    ApiResult<EmployeeDTO> getAuthorizedEmployeeDTO(@RequestHeader(RestConstants.AUTHORIZATION_HEADER) String token);

    @RequestMapping(method = RequestMethod.GET, value = GET_CLIENT_BY_ID, consumes = "application/json")
    ApiResult<ClientDTO> getClientDTO(@PathVariable(value = "userId") UUID userId,
                                      @RequestHeader(RestConstants.AUTHORIZATION_HEADER) String token);

    @RequestMapping(method = RequestMethod.GET, value = "api/auth/v1/internal/employee/{operatorId}", consumes = "application/json")
    ApiResult<EmployeeDTO> getEmployeeDTO(@PathVariable("operatorId") UUID operatorId, @RequestHeader(RestConstants.AUTHORIZATION_HEADER) String token);

    @RequestMapping(method = RequestMethod.GET, value = "api/auth/v1/internal/currier/{currierId}", consumes = "application/json")
    ApiResult<CurrierDTO> getCurrierDTO(@PathVariable("currierId") UUID currierId, @RequestHeader(RestConstants.AUTHORIZATION_HEADER) String token);

    @RequestMapping(method = RequestMethod.POST, value = "api/auth/v1/internal/client", consumes = "application/json")
    ApiResult<ClientDTO> getClientDTOAndSet(@RequestBody ClientDTO clientDTO, @RequestHeader(RestConstants.AUTHORIZATION_HEADER) String token);

    @GetMapping(value = VERIFICATION_BY_PHONE, consumes = "applicaton/json")
    ApiResult<?> verificationPhone(@PathVariable("phoneNumber") String phoneNumber, @RequestHeader(RestConstants.AUTHORIZATION_HEADER) String token);
}
