package uz.pdp.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.entity.Page;
import uz.pdp.entity.enums.PageEnum;

/**
 * Me: muhammadqodir
 * Project: app-auth/IntelliJ IDEA
 * Date:Tue 11/10/22 21:20
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageDTO {

    private Long id;
    private PageEnum page;
    private int priority;
    private String title;

    public static PageDTO mapToDTO(Page page) {
        return new PageDTO(page.getId(), page.getPage(), page.getPriority(), page.getPage().name().toLowerCase());
    }

}
