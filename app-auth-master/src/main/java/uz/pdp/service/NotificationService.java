package uz.pdp.service;

import uz.pdp.payload.AddNotificationDTO;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.NotificationDTO;

import java.util.List;

public interface NotificationService {

    ApiResult<Boolean> add(AddNotificationDTO addNotificationDTO);

    ApiResult<NotificationDTO> edit(NotificationDTO notificationDTO);

    ApiResult<NotificationDTO> get(Long id);

    ApiResult<List<NotificationDTO>> getAll();

    ApiResult<Boolean> delete();

}
