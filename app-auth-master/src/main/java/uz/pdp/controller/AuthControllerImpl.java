package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.SignDTO;
import uz.pdp.payload.SignInForEmployeeDTO;
import uz.pdp.payload.TokenDTO;
import uz.pdp.service.AuthService;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthControllerImpl implements AuthController{
    private final AuthService authService;

    public ApiResult<Boolean> signUp(@RequestBody @Valid SignDTO signDTO) {
        return authService.signUp(signDTO);
    }

    public ApiResult<?> verificationPhoneNumber(@PathVariable String phoneNumber) {
        return authService.verificationPhoneNumber(phoneNumber);
    }

    @Override
    public ApiResult<TokenDTO> signInForEmployee(@Valid SignInForEmployeeDTO signInForEmployeeDTO) {
        return authService.signInForEmployee(signInForEmployeeDTO);
    }

    @Override
    public ApiResult<TokenDTO> refreshToken(String accessToken, String refreshToken) {
        return authService.refreshToken(accessToken, refreshToken);
    }


}
