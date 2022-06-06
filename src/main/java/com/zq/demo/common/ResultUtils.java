package com.zq.demo.common;

/**
 * 2022/05/30 0:03 阿庆
 * 此类的作用是将 BaseResponse 再进行封装，防止多次  new BaseResponse
 */
public class ResultUtils {

    /**
     *
     * @param data 以 json 传递数据
     * @param <T> 任意类型
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse(0,data,"ok");
    }


    /**
     *
     * @param code 错误的状态
     * @return
     */
    public static  BaseResponse error(ErrorCode code) {
        return new BaseResponse<>(code);
    }

    /**
     *
     * @param code 错误的状态
     * @return
     */
    public static  BaseResponse error(ErrorCode code,String message,String description) {
        return new BaseResponse(code.getCode(),null,message,description);
    }

    /**
     *
     * @param code 错误的状态
     * @return
     */
    public static  BaseResponse error(ErrorCode code,String description) {
        return new BaseResponse(code.getCode(),code.getMessage(),description);
    }

    /**
     *
     * @param code 错误的状态
     * @return
     */
    public static  BaseResponse error(int code,String message,String description) {
        return new BaseResponse(code,null,message,description);
    }
}
