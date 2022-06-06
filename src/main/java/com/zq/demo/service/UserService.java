package com.zq.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zq.demo.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author zq
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2022-05-23 23:30:27
*/
public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param userAccount       账户名
     * @param userPassword      密码
     * @param checkPassword     校验密码
     * @return  存储成功的结果
     */

    long userRegister(String userAccount,String userPassword,String checkPassword);


    /**
     *
     * @param userAccount          账户名
     * @param userPassword          密码
     * @param request    用来发送Session
     * @return 登录成功
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     *  用户脱敏
     * @param user
     * @return 返回安全的用户
     */
    User gateSafetUser(User user);


    /**
     * 登出
     */
    int logout(HttpServletRequest request);

}
