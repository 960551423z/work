package com.zq.demo.service.impl;

import com.zq.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


/**
 * 2022/05/23 23:42 阿庆
 */
@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    public void add(){
        String userAccount = "lisi";
        String userPassword = "123456";
        String checkPassword = "123456";
        long l = userService.userRegister(userAccount, userPassword, checkPassword);
        System.out.println(l);
    }

}