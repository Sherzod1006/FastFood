package uz.pdp.apporder.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.apporder.entity.Feedback;
import uz.pdp.apporder.payload.FeedbackDTO;
import uz.pdp.appproduct.dto.ApiResult;

import javax.validation.Valid;

@RequestMapping(FeedbackController.PATH_BASE)
public interface FeedbackController {

    String PATH_BASE = "/api/order/v1/feedback";
    String ADD_BY_OPERATOR_PATH = "add-by-operator";
    String ADD_CLIENT_PATH = "add";
    String CHANGE_STATUS_PATH = "change-status";

    @PostMapping(ADD_BY_OPERATOR_PATH)
    ApiResult<Boolean> addByOperator(@Valid @RequestBody FeedbackDTO feedbackDTO);

    @PostMapping(ADD_CLIENT_PATH)
    ApiResult<?> add(@Valid @RequestBody FeedbackDTO feedbackDTO);

    @PutMapping(CHANGE_STATUS_PATH)
    ApiResult<Feedback> changeStatus(@RequestParam("id") Integer id,
                                     @RequestParam("accept") Boolean accept);

}