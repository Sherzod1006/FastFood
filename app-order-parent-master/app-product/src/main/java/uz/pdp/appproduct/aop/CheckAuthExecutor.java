package uz.pdp.appproduct.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.ClientDTO;
import uz.pdp.appproduct.dto.EmployeeDTO;
import uz.pdp.appproduct.exceptions.RestException;
import uz.pdp.appproduct.util.CommonUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckAuthExecutor {
    private final AuthFeign openFeign;

    @Before(value = "@annotation(checkAuth)")
    public void checkAuth(CheckAuth checkAuth) {
        getUserDTOIfIdNullThrow(checkAuth);
    }

    @Before(value = "@annotation(checkAuthEmpl)")
    public void checkAuth(CheckAuthEmpl checkAuthEmpl) {
        getUserDTOIfIdNullThrow(checkAuthEmpl);
    }

    public void getUserDTOIfIdNullThrow(CheckAuth checkAuth) {

        String token = CommonUtils.getCurrentRequest().getHeader("Authorization");

        if (Objects.isNull(token))
            throw RestException.restThrow("Bad Request", HttpStatus.UNAUTHORIZED);

        ApiResult<ClientDTO> authorizedClientDTO = openFeign.getAuthorizedClientDTO(token);

        ClientDTO clientDTO = Objects.requireNonNull(authorizedClientDTO.getData());

        if (Objects.isNull(clientDTO.getUserId()))
            throw RestException.restThrow("Bad Request", HttpStatus.UNAUTHORIZED);

        setRequestUserDTO(clientDTO);
    }

    public void getUserDTOIfIdNullThrow(CheckAuthEmpl checkAuthEmpl) {


        String token = CommonUtils.getCurrentRequest().getHeader("Authorization");

        if (Objects.isNull(token))
            throw RestException.restThrow("Bad Request", HttpStatus.UNAUTHORIZED);

        ApiResult<EmployeeDTO> authorizedEmployeeDTO = openFeign.getAuthorizedEmployeeDTO(token);

        EmployeeDTO operatorDTO = Objects.requireNonNull(authorizedEmployeeDTO.getData());

        if (Objects.isNull(operatorDTO.getId()))
            throw RestException.restThrow("Bad Request", HttpStatus.UNAUTHORIZED);

        if (checkAuthEmpl.permissions().length > 0) {
            if (Arrays.stream(checkAuthEmpl
                            .permissions())
                    .noneMatch(k -> operatorDTO
                            .getPermissions()
                            .contains(k.name())))
                throw RestException.restThrow("No Permission, Restricted", HttpStatus.FORBIDDEN);
        }

        setRequestEmployeeDTO(operatorDTO);
    }

    private void setRequestEmployeeDTO(EmployeeDTO operatorDTO) {
        HttpServletRequest currentRequest = CommonUtils.getCurrentRequest();
        if (Objects.nonNull(currentRequest))
            currentRequest.setAttribute("currentUser", operatorDTO);
    }

    private void setRequestUserDTO(ClientDTO clientDTO) {
        HttpServletRequest currentRequest = CommonUtils.getCurrentRequest();
        if (Objects.nonNull(currentRequest))
            currentRequest.setAttribute("currentUser", clientDTO);
    }


}
