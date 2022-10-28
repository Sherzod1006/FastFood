package uz.pdp.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.payload.AddNotificationDTO;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.NotificationDTO;
import uz.pdp.util.RestConstants;

import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping(NotificationController.NOTIFICATION_CONTROLLER_BASE_PATH)
public interface NotificationController {
    String NOTIFICATION_CONTROLLER_BASE_PATH = RestConstants.SERVICE_BASE_PATH + "notification";

    String ADD_PATH = "/add";

    String EDIT_PATH = "/edit";

    String GET_PATH = "/get/{id}";

    String GET_ALL_PATH = "/list";

    String DELETE_PATH = "/delete/{id}";

    @PostMapping(value = ADD_PATH)
    ApiResult<Boolean> add(@RequestBody @NotNull AddNotificationDTO addNotificationDTO);

    @PutMapping(value = EDIT_PATH)
    ApiResult<NotificationDTO> edit(@RequestBody @NotNull NotificationDTO notificationDTO);

    @GetMapping(value = GET_PATH)
    ApiResult<NotificationDTO> get(@PathVariable Long id);

    @GetMapping(value = GET_ALL_PATH)
    ApiResult<List<NotificationDTO>> getAllNotifications();


    @DeleteMapping(value = DELETE_PATH)
    ApiResult<Boolean> delete(@PathVariable Long id);
}
