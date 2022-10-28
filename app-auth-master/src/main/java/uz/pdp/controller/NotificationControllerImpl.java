package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.payload.AddNotificationDTO;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.NotificationDTO;
import uz.pdp.service.NotificationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationControllerImpl implements NotificationController {

    private final NotificationService notificationService;

    @Override
    public ApiResult<Boolean> add(AddNotificationDTO addNotificationDTO) {
        return notificationService.add(addNotificationDTO);
    }

    @Override
    public ApiResult<NotificationDTO> edit(NotificationDTO notificationDTO) {
        return notificationService.edit(notificationDTO);
    }

    @Override
    public ApiResult<NotificationDTO> get(Long id) {
        return notificationService.get(id);
    }

    @Override
    public ApiResult<List<NotificationDTO>> getAllNotifications() {
        return notificationService.getAll();
    }

    @Override
    public ApiResult<Boolean> delete(Long id) {
        return notificationService.delete();
    }
}
