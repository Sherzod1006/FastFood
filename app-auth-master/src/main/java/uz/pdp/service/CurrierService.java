package uz.pdp.service;

import uz.pdp.payload.ApiResult;
import uz.pdp.payload.CurrierDTO;


import java.util.List;
import java.util.UUID;

public interface CurrierService {
    ApiResult<CurrierDTO> add(CurrierDTO currierDTO);

    ApiResult<Boolean> delete(UUID id);

    ApiResult<List<CurrierDTO>> getCurrierList();

    ApiResult<Boolean> edit(CurrierDTO currierDTO, UUID id);

    ApiResult<CurrierDTO> getCurrier(UUID id);

    ApiResult<List<CurrierDTO>> getCurrierByStatus(boolean status);
}
