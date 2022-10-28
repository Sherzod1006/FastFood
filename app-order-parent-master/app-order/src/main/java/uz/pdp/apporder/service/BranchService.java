package uz.pdp.apporder.service;

import uz.pdp.apporder.entity.Branch;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.BranchDTO;

import java.util.List;

public interface BranchService {

    ApiResult<BranchDTO> add(BranchDTO branchDTO);

    ApiResult<Branch> getById(Integer id);

    ApiResult<List<Branch>> getAll();

    ApiResult<Boolean> deleteById(Integer id);

    ApiResult<Boolean> delete();

    ApiResult<Branch> edit(Integer id, BranchDTO branchDTO);

    ApiResult<String> geoLocation(Double lat, Double lng);
}
