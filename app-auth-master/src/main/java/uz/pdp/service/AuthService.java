package uz.pdp.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import uz.pdp.entity.User;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.SignDTO;
import uz.pdp.payload.SignInForEmployeeDTO;
import uz.pdp.payload.TokenDTO;


public interface AuthService extends UserDetailsService {
    ApiResult<Boolean> signUp(SignDTO signDTO);

    ApiResult<?> verificationPhoneNumber(String phoneNumber);

    ApiResult<TokenDTO> signInForEmployee(SignInForEmployeeDTO signDTO);

    ApiResult<TokenDTO> refreshToken(String accessToken, String refreshToken);

}
