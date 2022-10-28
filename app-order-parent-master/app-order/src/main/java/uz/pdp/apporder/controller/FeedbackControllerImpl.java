package uz.pdp.apporder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apporder.entity.Feedback;
import uz.pdp.apporder.payload.FeedbackDTO;
import uz.pdp.apporder.service.FeedbackService;
import uz.pdp.appproduct.aop.CheckAuth;
import uz.pdp.appproduct.aop.CheckAuthEmpl;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.enums.PermissionEnum;

@RestController
@RequiredArgsConstructor
public class FeedbackControllerImpl implements FeedbackController{

    private final FeedbackService feedbackService;

    @Override
    @CheckAuthEmpl(permissions = {PermissionEnum.ADD_ORDER})
    public ApiResult<Boolean> addByOperator(FeedbackDTO feedbackDTO) {
        return feedbackService.addByOperator(feedbackDTO);
    }

    @Override
    @CheckAuth(permissions = {PermissionEnum.ADD_ORDER})
    public ApiResult<?> add(FeedbackDTO feedbackDTO) {
        return feedbackService.add(feedbackDTO);
    }

    @Override
    @CheckAuthEmpl(permissions = {PermissionEnum.CHANGE_STATUS})
    public ApiResult<Feedback> changeStatus(Integer id, Boolean accept) {
        return feedbackService.changeStatus(id, accept);
    }
}
