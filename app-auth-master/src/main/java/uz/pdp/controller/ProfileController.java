package uz.pdp.controller;

import org.apache.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.entity.Client;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.response_DTO.ClientDTO;
import uz.pdp.util.RestConstants;


@RequestMapping(path = ProfileController.PROFILE_CONTROLLER_BASE_PATH)
public interface ProfileController {


    String PROFILE_CONTROLLER_BASE_PATH = RestConstants.SERVICE_BASE_PATH + "profile";

    String READ_PATH = "/read-profile";

    String EDIT_PATH = "/edit-profile";

    /**
     * Access token berilganda client ma'lumotlarini qaytarish
     *
     * @param accessToken = @accessToken
     * @return Client
     */
    @GetMapping(value = READ_PATH)
    ApiResult<Client> getClientProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);


    /**
     * ClientDTO berilsa Client ma'lumotlarini update qilish
     *
     * @param clientDTO = clientDTO
     * @return @Client
     */
    @PutMapping(value = EDIT_PATH)
    ApiResult<Client> editClientProfile(ClientDTO clientDTO);


}
