package uz.pdp.appproduct.dto;

import lombok.Getter;
import java.util.*;

@Getter
public class SearchingDTO {


    private List<String> columns = new ArrayList<>();

    private String value = "";
}
