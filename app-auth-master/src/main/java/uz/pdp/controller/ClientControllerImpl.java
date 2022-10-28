package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.add_DTO.AddClientDTO;
import uz.pdp.payload.response_DTO.ClientDTO;
import uz.pdp.payload.filterPayload.ClientDTOFilter;
import uz.pdp.payload.filterPayload.ViewDTO;
import uz.pdp.service.ClientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController{

    private final ClientService clientService;

    @Override
    public ApiResult<List<ClientDTO>> getAll() {
        return clientService.getAll();
    }

    @Override
    public ApiResult<ClientDTO> get(UUID id) {
        return clientService.get(id);
    }

    @Override
    public ApiResult<Boolean> add(AddClientDTO addClientDTO) {
        return clientService.add(addClientDTO);
    }

    @Override
    public ApiResult<Boolean> edit(ClientDTO clientDTO) {
        return clientService.edit(clientDTO);
    }

    @Override
    public ApiResult<Boolean> delete(UUID id) {
        return clientService.delete(id);
    }

    @Override
    public ApiResult<List<ClientDTOFilter>> getALl(ViewDTO viewDTO, int page, int size) {

        return clientService.getAllClients(viewDTO, page, size);
    }
}
