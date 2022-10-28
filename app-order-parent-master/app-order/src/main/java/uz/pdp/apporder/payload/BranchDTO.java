package uz.pdp.apporder.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.Branch;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchDTO {

    private String name;

    private String city;

    private String street;

    private Integer postalCode;

    public  static BranchDTO mapBranchToBranchDTO(Branch branch){
        return new BranchDTO(
                branch.getName(),
                branch.getAddress().getCity(),
                branch.getAddress().getStreet(),
                branch.getAddress().getPostalCode()
                );
    }
}
