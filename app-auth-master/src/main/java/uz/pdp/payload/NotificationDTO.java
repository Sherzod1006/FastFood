package uz.pdp.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.entity.enums.NotificationTypeEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    private Long id;

    private String title;

    private String description;

    private Long time;

    private NotificationTypeEnum notificationType;

    private Byte[] picture;


}
