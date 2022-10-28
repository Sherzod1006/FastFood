package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.Feedback;
import uz.pdp.apporder.payload.FeedbackDTO;
import uz.pdp.apporder.repository.FeedbackRepository;
import uz.pdp.appproduct.aop.AuthFeign;
import uz.pdp.appproduct.dto.ApiResult;
import uz.pdp.appproduct.dto.ClientDTO;
import uz.pdp.appproduct.dto.EmployeeDTO;
import uz.pdp.appproduct.exceptions.RestException;
import uz.pdp.appproduct.util.CommonUtils;
import uz.pdp.appproduct.util.RestConstants;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService{
    private final AuthFeign openFeign;

    private final FeedbackRepository feedbackRepository;

    @Override
    public ApiResult<Boolean> addByOperator(FeedbackDTO feedbackDTO) {
        EmployeeDTO operator = getOperator();
        Feedback feedback = mapFeedbackDTOToFeedback(feedbackDTO);
        feedback.setEmployeeId(operator.getId());

        feedbackRepository.save(feedback);

        return ApiResult.successResponse();
    }



    @Override
    public ApiResult<?> add(FeedbackDTO feedbackDTO) {
        String token = CommonUtils.getCurrentRequest().getHeader(RestConstants.AUTHORIZATION_HEADER);
        ClientDTO clientDTO = getClient(token);
        ApiResult<?> apiResult = openFeign.verificationPhone(clientDTO.getPhoneNumber(), token);
        if (!apiResult.isSuccess())
            return  ApiResult.failResponse("Phone number not valid", HttpStatus.FORBIDDEN.value());

        feedbackRepository.save(mapFeedbackDTOToFeedback(feedbackDTO));

        return ApiResult.successResponse();

    }

    @Override
    public ApiResult<Feedback> changeStatus(Integer id, Boolean accept) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("Bunday id dagi feedback mavjud emas!", HttpStatus.NOT_FOUND));

        feedback.setAccept(accept);
        feedbackRepository.save(feedback);
        return ApiResult.successResponse(feedback);
    }

    private Feedback mapFeedbackDTOToFeedback(FeedbackDTO feedbackDTO){
        return Feedback.builder()
                .text(feedbackDTO.getText())
                .phoneNumber(feedbackDTO.getPhoneNumber())
                .accept(null)
                .createdAt(LocalDateTime.now())
                .build();

    }

    private EmployeeDTO getOperator() {
        String token = CommonUtils.getCurrentRequest().getHeader(RestConstants.AUTHORIZATION_HEADER);

        if (Objects.isNull(token))
            throw RestException.restThrow("Bad Request", HttpStatus.UNAUTHORIZED);

        ApiResult<EmployeeDTO> authorizedEmployeeDTO = openFeign.getAuthorizedEmployeeDTO(token);
        return Objects.requireNonNull(authorizedEmployeeDTO.getData());
    }

    private ClientDTO getClient(String token){

        if (Objects.isNull(token))
            throw RestException.restThrow("Bad Request", HttpStatus.UNAUTHORIZED);

        ApiResult<ClientDTO> authorizedClientDTO = openFeign.getAuthorizedClientDTO(token);

        return Objects.requireNonNull(authorizedClientDTO.getData());
    }
}
