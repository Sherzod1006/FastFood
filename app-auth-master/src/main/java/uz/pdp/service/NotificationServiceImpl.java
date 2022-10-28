package uz.pdp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.entity.Notification;
import uz.pdp.payload.AddNotificationDTO;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.NotificationDTO;
import uz.pdp.repository.NotificationRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public ApiResult<Boolean> add(AddNotificationDTO addNotificationDTO) {
        Notification notification = new Notification();
        notification.setNotificationType(addNotificationDTO.getNotificationType());
        notification.setTime(addNotificationDTO.getTime());
        notification.setTitle(addNotificationDTO.getTitle());
        notification.setDescription(addNotificationDTO.getDescription());
        notification.setPicture(addNotificationDTO.getPicture());
        notificationRepository.save(notification);

        return ApiResult.successResponse();
    }


    @Override
    public ApiResult<NotificationDTO> edit(NotificationDTO notificationDTO) {
        return null;
    }

    @Override
    public ApiResult<NotificationDTO> get(Long id) {
        return null;
    }

    @Override
    public ApiResult<List<NotificationDTO>> getAll() {
        return null;
    }

    @Override
    public ApiResult<Boolean> delete() {
        return null;
    }

//    private Notification mapNotificationDTOToNotification(NotificationDTO notificationDTO){
//       return new Notification();
//    }
}
