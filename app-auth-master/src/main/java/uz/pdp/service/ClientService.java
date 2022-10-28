package uz.pdp.service;

import uz.pdp.payload.ApiResult;
import uz.pdp.payload.add_DTO.AddClientDTO;
import uz.pdp.payload.response_DTO.ClientDTO;
import uz.pdp.payload.filterPayload.ClientDTOFilter;
import uz.pdp.payload.filterPayload.ViewDTO;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    ApiResult<List<ClientDTO>> getAll();
    ApiResult<ClientDTO> get(UUID id);
    ApiResult<Boolean> add(AddClientDTO clientDTO);

    ApiResult<Boolean> edit(ClientDTO clientDTO);

    ApiResult<Boolean> delete(UUID id);

    ApiResult<List<ClientDTOFilter>> getAllClients(ViewDTO viewDTO, int page, int size);

}
