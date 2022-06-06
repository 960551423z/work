package com.zq.demo.exception;

import com.zq.demo.common.BaseResponse;
import com.zq.demo.common.ErrorCode;
import com.zq.demo.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 2022/05/30 15:03 阿庆
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 捕获 BuBusinessException 的异常
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        log.error("businessException: "+e.getMessage(), e);
        return ResultUtils.error(e.getCode(),e.getMessage(), e.getDescription());
    }

    // 捕获 别的异常（如运行时异常） 的异常
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException",e);
        // 内部异常 e.getMessage()是运行给的提示然后赋值给自定义的 message
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
    }
}
