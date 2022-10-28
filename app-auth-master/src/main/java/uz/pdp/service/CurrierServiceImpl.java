package uz.pdp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Currier;
import uz.pdp.entity.User;
import uz.pdp.exceptions.RestException;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.CurrierDTO;
import uz.pdp.repository.CurrierRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrierServiceImpl implements CurrierService {
    private final CurrierRepository currierRepository;
    private final UserService userService;

    @Override
    public ApiResult<CurrierDTO> add(CurrierDTO currierDTO) {

        carNumberOrDriverLicenseExistsThrow(currierDTO);

        Currier currier = mapToCurrier(currierDTO);

        currierRepository.save(currier);
        return ApiResult.successResponse(mapCurrierTOCurrierDto(currier));
    }


    @Override
    public ApiResult<Boolean> delete(UUID id) {
        currierRepository.deleteById(id);
        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<List<CurrierDTO>> getCurrierList() {
        List<Currier> currierList = currierRepository.findAll();
        List<CurrierDTO> currierDTOList = mapCurriersToCurrierDTOList(currierList);
        return ApiResult.successResponse(currierDTOList);
    }

    @Override
    public ApiResult<Boolean> edit(CurrierDTO currierDTO, UUID id) {
        Currier currier = currierRepository
                .findById(id)
                .orElseThrow(() -> RestException.restThrow("", HttpStatus.CONFLICT));

        currier.setBirthDate(currierDTO.getBirthDate());
        currier.setFirstName(currierDTO.getFirstName());
        currier.setLastName(currierDTO.getLastName());
        currier.setCarNumber(currierDTO.getCarNumber());
        currier.setDriverLicense(currierDTO.getDriverLicense());
        currierRepository.save(currier);
        return ApiResult.successResponse("successfully edited");
    }

    @Override
    public ApiResult<CurrierDTO> getCurrier(UUID id) {
        Currier currier = currierRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow(
                        "NO currier available", HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(mapCurrierTOCurrierDto(currier));
    }

    @Override
    public ApiResult<List<CurrierDTO>> getCurrierByStatus(boolean status) {
        List<Currier> curriersByOnline = currierRepository.getCurriersByOnline(status);
        List<CurrierDTO> currierDTOList = mapCurriersToCurrierDTOList(curriersByOnline);
        return ApiResult.successResponse(currierDTOList);
    }


    public Currier mapToCurrier(CurrierDTO currierDTO) {
        User user = userService.findByPhoneNumberIfNotCreate(currierDTO.getPhoneNumber());
        return new Currier(
                user,
                currierDTO.getBirthDate(),
                currierDTO.getFirstName(),
                currierDTO.getLastName(),
                currierDTO.getCarNumber(),
                currierDTO.getDriverLicense()
        );
    }

    private CurrierDTO mapCurrierTOCurrierDto(Currier currier) {
        return new CurrierDTO(
                currier.getBirthDate(),
                currier.getFirstName(),
                currier.getLastName(),
                currier.getCarNumber(),
                currier.getDriverLicense(),
                currier.getUser().getPhoneNumber()
        );
    }

    private List<CurrierDTO> mapCurriersToCurrierDTOList(List<Currier> currierList) {
        return
                currierList
                        .stream()
                        .map(this::mapCurrierTOCurrierDto)
                        .collect(Collectors.toList());
    }

    /**
     * checks driverʻs car number or license exists
     * throws currier already existed exception
     * @param currierDTO
     */
    private void carNumberOrDriverLicenseExistsThrow(CurrierDTO currierDTO) {
        if (currierRepository.existsByCarNumber(currierDTO.getCarNumber())
                || currierRepository.existsByDriverLicense(currierDTO.getDriverLicense()))
            throw RestException.restThrow("Such currier already exists", HttpStatus.CONFLICT);
    }

}
