package com.zq.demo.model.request;

import lombok.Data;

/**
 * 2022/05/24 19:50 阿庆
 */
@Data
public class UserLoginRequest {

    private String userAccount;
    private String userPassword;

}
