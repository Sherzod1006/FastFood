package uz.pdp.apporder.service;

import uz.pdp.apporder.entity.Feedback;
import uz.pdp.apporder.payload.FeedbackDTO;
import uz.pdp.appproduct.dto.ApiResult;

public interface FeedbackService {
    ApiResult<Boolean> addByOperator(FeedbackDTO feedbackDTO);

    ApiResult<?> add(FeedbackDTO feedbackDTO);

    ApiResult<Feedback> changeStatus(Integer id, Boolean accept);
}
