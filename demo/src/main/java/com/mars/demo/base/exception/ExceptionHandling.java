package com.mars.demo.base.exception;

import com.mars.demo.base.bean.ResponseResult;
import com.mars.demo.base.enums.ReturnMessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 全局统一异常捕捉
 * @author xzp
 * @date 2019/10/9 10:35 上午
 **/
@RestControllerAdvice
public class ExceptionHandling {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(RuntimeException.class)
    public Object RuntimeException(Exception ex, HttpServletRequest request) {
        String message = ex.getMessage();
        if(message == null) {
            message = "空指针异常";
        }else if(message.contains("cannot be cast to") == true){
            message = "数据类型错误";
        }
        ex.printStackTrace();
        logger.error("全局异常捕捉,接口路径:" + request.getRequestURI() + ",异常信息:" + message);
        return ResponseResult.responseError(ReturnMessageType.MSG_FAIL_500.getCode(), message);
    }

    @ExceptionHandler(ParamExceptionHandling.class)
    public Object ParamExceptionHandling(ParamExceptionHandling ex) {
        ex.printStackTrace();
        logger.error("自定义参数校验异常,异常信息:" + ex.getMsg());
        return ResponseResult.responseError(ex.getCode(), ex.getMsg());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Object IllegalArgumentException(Exception ex) {
        ex.printStackTrace();
        logger.error("全局异常,异常信息:" + ex.getMessage());
        return ResponseResult.responseError(ReturnMessageType.MSG_FAIL_500.getCode(), ex.getMessage());
    }

    /**
     * 参数输入不符合要求
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<String> errorMsgs = new ArrayList<>();
        allErrors.forEach( objectError -> {
            FieldError fieldError = (FieldError) objectError;
            errorMsgs.add( fieldError.getDefaultMessage());
        } );
        ex.printStackTrace();
        logger.error("注解参数校验异常,错误信息:" + errorMsgs.get(0));
        return ResponseResult.responseError(ReturnMessageType.MSG_FAIL_50005.getCode(), errorMsgs.toString());
    }


}
