package com.zq.demo.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 2022/05/29 23:55 阿庆
 * 处理异常的类
 * 此时不知道到底要返回啥类，
 *      返回的数据类型为 NULl 还是 user 或是其他的
 *          所以使用泛型 T 来代替任何返回类型
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T date;

    private String message;

    private String description;

    // 多写几组构造函数，以便接收不同的个数
    public BaseResponse(int code, T date, String message, String description) {
        this.code = code;
        this.date = date;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T date, String message) {
        this(code, date,message,"");
    }

    public BaseResponse(int code, T date) {
        this(code,date,"","");
    }

    // 定义一个返回错误的构造器
    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }
}
