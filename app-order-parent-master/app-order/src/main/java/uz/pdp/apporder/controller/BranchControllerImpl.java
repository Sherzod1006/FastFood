package uz.pdp.apporder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apporder.entity.Branch;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.BranchDTO;
import uz.pdp.apporder.service.BranchService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BranchControllerImpl implements BranchController {

    private final BranchService branchService;

    @Override
    public ApiResult<BranchDTO> add(BranchDTO branchDTO) {
        ApiResult<BranchDTO> add = branchService.add(branchDTO);
        return add;
    }

    @Override
    public ApiResult<Branch> getById(Integer id) {
        ApiResult<Branch> byId = branchService.getById(id);
        return byId;
    }

    @Override
    public ApiResult<List<Branch>> getAll() {
        ApiResult<List<Branch>> all = branchService.getAll();
        return all;
    }

    @Override
    public ApiResult<Boolean> deleteById(Integer id) {
        ApiResult<Boolean> booleanApiResult = branchService.deleteById(id);
        return booleanApiResult;
    }

    @Override
    public ApiResult<Boolean> delete() {
        ApiResult<Boolean> delete = branchService.delete();
        return delete;
    }

    @Override
    public ApiResult<Branch> edit(Integer id, BranchDTO branchDTO) {
        ApiResult<Branch> edit = branchService.edit(id, branchDTO);
        return edit;
    }

    @Override
    public ApiResult<String> geoLocation(Double lat, Double lng) {
        return branchService.geoLocation(lat, lng);
    }
}
