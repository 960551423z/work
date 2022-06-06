package com.zq.demo.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zq.demo.common.ErrorCode;
import com.zq.demo.common.ResultUtils;
import com.zq.demo.exception.BusinessException;
import com.zq.demo.model.domain.User;
import com.zq.demo.service.UserService;
import com.zq.demo.mapper.UserMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zq.demo.constant.Constant.LOGIN_SUCCESS_SESSION;
import static com.zq.demo.constant.Constant.SALT;

/**
* @author zq
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2022-05-23 23:30:27
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    @SneakyThrows
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 同时判断多个字符串是否为空
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"用户或密码为空");
        }

        // 判断密码长度大于4
        if (userAccount.length() < 4 ) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名太短");
        }

        // 判断 密码 和 校验密码
        if (userPassword.length() <6 || checkPassword.length() <6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码太短");
        }

        // 判断 账户 是不是特殊字符
        String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*()——+|{}【】‘；：”“’。， 、？]";
        Matcher matcher = Pattern.compile(regEx).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"特殊字符");
        }

        // 判断两次密码是否相等
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次密码不同");
        }

        // 判断数据库中有没有此用户 (1表示有，0表示没有)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        Long aLong = userMapper.selectCount(queryWrapper);
        // 此时找到表示有，所以返回，否则加密添加
        if (aLong > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户存在");
        }

        // 加密密码
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        //加盐操作增加常量值
        String securityPwd = Base64.encodeBase64String(md5.digest((userPassword + SALT).getBytes()));

        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(securityPwd);

        // 此时还要进行判断添加是否成功
        int insert = userMapper.insert(user);
        if (insert <=0 ){
            throw new BusinessException(ErrorCode.INSERT_ERROR,"添加用户失败");
        }
        return insert;
    }

    @SneakyThrows
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 同时判断多个字符串是否为空
        if (StringUtils.isAnyBlank(userAccount,userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"用户或密码为空");
        }

        // 判断密码长度大于4
        if (userAccount.length() < 4 ) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名太短");
        }

        // 判断 密码 和 校验密码
        if (userPassword.length() <6 ) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码太短");
        }

        // 判断 账户 是不是特殊字符
        String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*()——+|{}【】‘；：”“’。， 、？]";
        Matcher matcher = Pattern.compile(regEx).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名包含特殊字符");
        }

        // 判断数据库中有没有此用户 和 密码是否匹配
        // 加密密码
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        // 加盐操作增加常量值
        String securityPwd = Base64.encodeBase64String(md5.digest((userPassword + SALT).getBytes()));

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", securityPwd);
        // 查询是否有这一条数据，有则返回 User 对象，没有则返回空
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户或密码错误");
        }

        User safetUser = gateSafetUser(user);

        /* 如果上面逻辑都通过，则说明查到这个用户，此时将设置它的 session，由此来判断它是否是已经登录了
                  如果直接返回，则会把密码进行泄露给前台了，因此进行用户脱敏
         */
        request.getSession().setAttribute(LOGIN_SUCCESS_SESSION,safetUser);


        return safetUser;
    }

    @Override
    public User gateSafetUser(User user) {
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_AUTH,"未知~");
        }
        User safetUser = new User();
        safetUser.setId(user.getId());
        safetUser.setUserAccount(user.getUserAccount());
        safetUser.setPhone(user.getPhone());
        safetUser.setEmail(user.getEmail());
        safetUser.setIsAdmin(user.getIsAdmin());
        safetUser.setSex(user.getSex());
        safetUser.setCreateTime(new Date());
        safetUser.setImgUrl(user.getImgUrl());
        return safetUser;
    }

    /**
     * 移除登录态
     */
    @Override
    public int logout(HttpServletRequest request) {
        request.getSession().removeAttribute(LOGIN_SUCCESS_SESSION);
        return 1;
    }


}




