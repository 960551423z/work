package com.zq.demo.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 2022/05/24 18:42 阿庆
 * 实现序列化接口，方面后面做 redis 缓存时使用
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 517202650238250608L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;

}
