package uz.pdp.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.CurrierDTO;


import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@RequestMapping(path = CurrierController.CURRIER_BASE_PATH)
public interface CurrierController {

    String CURRIER_BASE_PATH = "/api/currier";
    String CURRIER_ADD_PATH = "/add";
    String GET_CURRIER_LIST = "/get-currier-list";
    String GET_CURRIER_LIST_BY_STATUS = "/get-currier-list-status";
    String GET_CURRIER = "/{id}";
    String PUT_CURRIER = "/{id}";
    String DELETE_CURRIER = "/{id}";


    @PostMapping(CURRIER_ADD_PATH)
    ApiResult<CurrierDTO> add(@Valid @RequestBody CurrierDTO currierDTO);

    @GetMapping(GET_CURRIER)
    ApiResult<CurrierDTO> getOne(@PathVariable UUID id);

    @GetMapping(GET_CURRIER_LIST)
    ApiResult<List<CurrierDTO>> getAll();
    @GetMapping(GET_CURRIER_LIST_BY_STATUS+"/{status}")
    ApiResult<List<CurrierDTO>> getAllByStatus(@PathVariable boolean status);

    @PutMapping(PUT_CURRIER)
    ApiResult<Boolean> edit(@RequestBody CurrierDTO currierDTO, @PathVariable UUID id);

    @DeleteMapping(DELETE_CURRIER)
    ApiResult<Boolean> delete(@PathVariable UUID id);

}
