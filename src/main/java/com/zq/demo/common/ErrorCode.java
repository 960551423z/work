package com.zq.demo.common;

/**
 * 2022/05/29 23:46 阿庆
 * 枚举错误的状态
 */
public enum ErrorCode {
    SUCCESS(0,"ok",""),
    PARAMS_ERROR(40000,"请求参数错误",""),
    NULL_ERROR(40001,"请求参数为空",""),
    NOT_ERROR(40002,"未登录",""),
    NOT_AUTH(40101,"没有权限",""),
    INSERT_ERROR(40010,"插入数据失败",""),
    SYSTEM_ERROR(50000,"系统内部异常","");


    /**
     * code：状态码
     * message：状态描述
     * description：错误描述详情
     */
    private final int code;
    private final String message;
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
