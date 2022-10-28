package uz.pdp.service;

import org.springframework.stereotype.Service;
import uz.pdp.entity.Client;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.response_DTO.ClientDTO;

@Service
public interface ProfileService {

    ApiResult<Client> getClientProfile(String accessToken);

    ApiResult<Client> editClientProfile(ClientDTO clientDTO);
}
