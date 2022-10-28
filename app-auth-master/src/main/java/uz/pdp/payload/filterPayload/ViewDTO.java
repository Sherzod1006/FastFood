package uz.pdp.payload.filterPayload;

import lombok.Getter;

import java.util.List;

@Getter
public class ViewDTO {

    private String searching;

    private List<SortingDTO> sorting;

    private FilterDTO filtering;
}
