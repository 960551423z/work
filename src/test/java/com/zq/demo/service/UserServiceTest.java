package com.zq.demo.service;
import java.util.Date;

import com.zq.demo.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 2022/05/23 23:34 阿庆
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;


    @Test
    public void addTest() {
        User user = new User();
        user.setUserAccount("zhangsan");
        user.setUserPassword("123456");
        user.setIsAdmin(0);
        user.setIsUse(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);

        boolean save = userService.save(user);
        System.out.println(user.getId());
    }
}