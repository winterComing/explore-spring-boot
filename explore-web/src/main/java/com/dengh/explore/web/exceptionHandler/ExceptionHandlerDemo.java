package com.dengh.explore.web.exceptionHandler;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author dengH
 * @title: ExceptionHandlerDemo
 * @description: TODO
 * @date 2019/8/6 17:41
 */
public class ExceptionHandlerDemo {

    /*@ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody*/
    /*public IsvOrderRefundBaseResponse<Object> paramValidateHandler(MethodArgumentNotValidException e){
        IsvOrderRefundBaseResponse<Object> objectIsvOrderRefundBaseResponse = new IsvOrderRefundBaseResponse<>();
        objectIsvOrderRefundBaseResponse.setRespCode(RefundEnum.PARAM_WRONG.getCode());
        objectIsvOrderRefundBaseResponse.setRespDesc(RefundEnum.PARAM_WRONG.getMsg() + e.getBindingResult().getFieldError().getField() + e.getBindingResult().getFieldError().getDefaultMessage());
        objectIsvOrderRefundBaseResponse.setRespTime(new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()));
        return objectIsvOrderRefundBaseResponse;
    }*/


    //        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//        Set<ConstraintViolation<IsvOrderRefundRequest>> validateResult = validator.validate(isvOrderRefundRequest, Default.class);
//
//        if (CollectionUtils.isNotEmpty(validateResult)){
//            String errorMsg = "";
//            for (ConstraintViolation constraintViolation: validateResult) {
//                errorMsg += constraintViolation.getPropertyPath();
//                errorMsg += constraintViolation.getMessage() + ";";
//            }
//            IsvOrderRefundBaseResponse<Object> objectIsvOrderRefundBaseResponse = new IsvOrderRefundBaseResponse<>();
//            objectIsvOrderRefundBaseResponse.setRespJournalNo(isvOrderRefundRequest.getRequestJournalNo());
//            objectIsvOrderRefundBaseResponse.setRespCode(RefundEnum.PARAM_WRONG.getCode());
//            objectIsvOrderRefundBaseResponse.setRespDesc(RefundEnum.PARAM_WRONG.getMsg() + errorMsg);
//            objectIsvOrderRefundBaseResponse.setRespTime(new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()));
//            return objectIsvOrderRefundBaseResponse;
//        }
}
