package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.pdp.apporder.entity.Branch;
import uz.pdp.apporder.exceptions.RestException;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.BranchDTO;
import uz.pdp.apporder.repository.BranchRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    @Override
    public ApiResult<BranchDTO> add(BranchDTO branchDTO) {
        if (!branchRepository.existsByName(branchDTO.getName())) {
            throw RestException.restThrow("This name already exists!", HttpStatus.BAD_REQUEST);
        }
        Branch branch = new Branch();
        branch.setName(branchDTO.getName());
        branch.getAddress().setCity(branchDTO.getCity());
        branch.getAddress().setStreet(branchDTO.getStreet());
        branch.getAddress().setPostalCode(branchDTO.getPostalCode());

        return ApiResult.successResponse("Added!");
    }

    @Override
    public ApiResult<Branch> getById(Integer id) {
        Optional<Branch> byId = branchRepository.findById(id);
        if (byId.isEmpty()) {
            throw RestException.restThrow("This id not exists!", HttpStatus.BAD_REQUEST);
        }
        Branch branch = byId.get();
        return ApiResult.successResponse(branch);
    }

    @Override
    public ApiResult<List<Branch>> getAll() {
        List<Branch> all = branchRepository.findAll();
        return ApiResult.successResponse(all);
    }

    @Override
    public ApiResult<Boolean> deleteById(Integer id) {
        Optional<Branch> byId = branchRepository.findById(id);
        if (byId.isEmpty()) {
            throw RestException.restThrow("This id not exists!", HttpStatus.BAD_REQUEST);
        }
        branchRepository.deleteById(id);
        return ApiResult.successResponse("Deleted");
    }

    @Override
    public ApiResult<Boolean> delete() {
        branchRepository.deleteAll();
        return ApiResult.successResponse("Deleted all");
    }

    @Override
    public ApiResult<Branch> edit(Integer id, BranchDTO branchDTO) {
        Optional<Branch> byId = branchRepository.findById(id);
        if (byId.isEmpty()) {
            throw RestException.restThrow("This id not exists!", HttpStatus.BAD_REQUEST);
        }
        Branch branch = new Branch();
        branch.setName(branchDTO.getName());
        branch.getAddress().setCity(branchDTO.getCity());
        branch.getAddress().setStreet(branchDTO.getStreet());
        branch.getAddress().setPostalCode(branchDTO.getPostalCode());
        return ApiResult.successResponse("Updated!");
    }

    /**
     * BU PULLIK API EKAN, TEKIN API TOPIB QOYSA ISHLAYDI ANIQ!
     *
     * @param lat
     * @param lng
     * @return String
     */
    @Override
    public ApiResult<String> geoLocation(Double lat, Double lng) {
        String url = "https://api-maps.yandex.ru/2.1/?apikey=506127d8-5d3d-41dd-8928-ba1de79e5850&lang=ru_RU&lat="+lat+"&lng="+lng;

        RestTemplate restTemplate = new RestTemplate();

        var data = Objects.requireNonNull(restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {})).getBody();

        return ApiResult.successResponse("success", data);
    }
}