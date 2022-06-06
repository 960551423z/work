package com.zq.demo.exception;

import com.zq.demo.common.ErrorCode;

/**
 * 2022/05/30 14:40 阿庆
 * RuntimeException 不用try catch 捕获,且不会退出程序
 */
public class BusinessException extends RuntimeException {
    
    private final int code;
    
    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode,String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
