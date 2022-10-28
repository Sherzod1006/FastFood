package uz.pdp.service;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import uz.pdp.entity.Client;
import uz.pdp.entity.User;
import uz.pdp.exceptions.RestException;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.response_DTO.ClientDTO;
import uz.pdp.repository.ClientRepository;
import uz.pdp.repository.UserRepository;

import java.util.Objects;


@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ClientRepository clientRepository;

    private final UserRepository userRepository;

    @Value("${jwt.access.key}")
    private String ACCESS_TOKEN_KEY;

    @Override
    public ApiResult<Client> getClientProfile(String accessToken) {

        String phoneNumber;
        accessToken = accessToken.substring(accessToken.indexOf("Bearer") + 6).trim();
        try {
            phoneNumber = Jwts
                    .parser()
                    .setSigningKey(ACCESS_TOKEN_KEY)
                    .parseClaimsJws(accessToken)
                    .getBody()
                    .getSubject();
        } catch (Exception ex) {
            throw RestException.restThrow("WRONG_ACCESS_TOKEN", HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> RestException.restThrow("This user is not found", HttpStatus.NOT_FOUND));

        Client client = clientRepository.findByUserId(user.getId())
                .orElseThrow(() -> RestException.restThrow("This client is not found", HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(client);
    }

    @Override
    public ApiResult<Client> editClientProfile(ClientDTO clientDTO) {
        Client client = clientRepository.findById(clientDTO.getId())
                .orElseThrow(() -> RestException.restThrow("This client is not found", HttpStatus.NOT_FOUND));

        client.setBirthDate(clientDTO.getBirthDate());
        client.setName(clientDTO.getName());
        if (!Objects.equals(clientDTO.getPhoneNumber(), client.getUser().getPhoneNumber()))
            throw RestException.restThrow("Phone number conflict", HttpStatus.CONFLICT);
        clientRepository.save(client);

        return ApiResult.successResponse(client);
    }
}
