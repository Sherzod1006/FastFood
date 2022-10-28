package uz.pdp.apporder.payload;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ViewDTO {

    private SearchingDTO searching = new SearchingDTO();

    private List<SortingDTO> sorting = new ArrayList<>();

    private OrderListDTO orderListDTO;
}
