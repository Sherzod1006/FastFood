package uz.pdp.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.entity.enums.PageEnum;

/**
 * Me: muhammadqodir
 * Project: app-auth/IntelliJ IDEA
 * Date:Tue 11/10/22 16:01
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class AddPageDTO {

    private PageEnum page;

    private Integer priority;

}
