package uz.pdp.apporder.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.apporder.entity.Branch;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.BranchDTO;

import java.util.List;

@RequestMapping("/api/branch")
public interface BranchController {

    @PostMapping("/add")
    ApiResult<BranchDTO> add(@RequestBody BranchDTO branchDTO);

    @GetMapping("{id}")
    ApiResult<Branch> getById(@PathVariable Integer id);

    @GetMapping
    ApiResult<List<Branch>> getAll();

    @DeleteMapping("/{id}")
    ApiResult<Boolean> deleteById(@PathVariable Integer id);

    @DeleteMapping
    ApiResult<Boolean> delete();

    @PutMapping("/{id}")
    ApiResult<Branch> edit(@PathVariable Integer id, @RequestBody BranchDTO branchDTO);

    @GetMapping("/map/{lat}/{lng}")
    ApiResult<String> geoLocation(@PathVariable Double lat, @PathVariable Double lng);
}
