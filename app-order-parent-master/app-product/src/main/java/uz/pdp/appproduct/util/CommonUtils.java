package uz.pdp.appproduct.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uz.pdp.appproduct.dto.ClientDTO;
import uz.pdp.appproduct.dto.CurrierDTO;
import uz.pdp.appproduct.dto.EmployeeDTO;
import uz.pdp.appproduct.exceptions.RestException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CommonUtils {

    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Optional.ofNullable(requestAttributes).map(ServletRequestAttributes::getRequest).orElse(null);
    }

    public static ClientDTO getCurrentClient() {
        ClientDTO currentUser = (ClientDTO) getCurrentRequest().getAttribute(RestConstants.REQUEST_ATTRIBUTE_CURRENT_USER);
        if (currentUser == null)
            throw RestException.restThrow("", HttpStatus.FORBIDDEN);
        return currentUser;
    }

    public static EmployeeDTO getCurrentEmployee() {
        EmployeeDTO currentUser = (EmployeeDTO) getCurrentRequest().getAttribute(RestConstants.REQUEST_ATTRIBUTE_CURRENT_USER);
        if (currentUser == null)
            throw RestException.restThrow("", HttpStatus.FORBIDDEN);
        return currentUser;
    }

    public static CurrierDTO getCurrentCurrier() {
        CurrierDTO currentUser = (CurrierDTO) getCurrentRequest().getAttribute(RestConstants.REQUEST_ATTRIBUTE_CURRENT_USER);
        if (currentUser == null)
            throw RestException.restThrow("", HttpStatus.FORBIDDEN);
        return currentUser;
    }


}
