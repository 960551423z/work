package com.zq.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zq.demo.common.BaseResponse;
import com.zq.demo.common.ErrorCode;
import com.zq.demo.common.ResultUtils;
import com.zq.demo.exception.BusinessException;
import com.zq.demo.model.domain.User;
import com.zq.demo.model.request.UserLoginRequest;
import com.zq.demo.model.request.UserRegisterRequest;
import com.zq.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

import static com.zq.demo.constant.Constant.IS_ADMIN;
import static com.zq.demo.constant.Constant.LOGIN_SUCCESS_SESSION;

/**
 * 2022/05/24 18:36 阿庆
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    // 发现要使用 userAccount 和 userPassword，checkPassword字段，因此此时新建一个包含这些字段的类（UserRegisterRequest）
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        // 为防止异常，此时判断一下 userRegisterRequest 不为空
        if (userRegisterRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }

        // 判断三个值有没有空值
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"请求参数为空");
        }

        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }


    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"请求参数为空");
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    // 得到用户的 登录态
    @GetMapping("/current")
    public User getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(LOGIN_SUCCESS_SESSION);
        User userCurrent = (User) userObj;
        if (userCurrent == null) {
            throw new BusinessException(ErrorCode.NOT_ERROR,"未登录成功");
        }

        // 可以从数据库中查询，对应频繁变化的用户
        Long id = userCurrent.getId();

        // todo 校验用户是否合法
        User user = userService.getById(id);
        return userService.gateSafetUser(user);
//        return ResultUtils.success(result);
    }

    // 登出
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"登出失败");
        }

        int result = userService.logout(request);
        return ResultUtils.success(result);
    }


    // 仅管理员可查询 （通过用户名查询）
    @GetMapping("/search")
    public List<User> searchUser(String userAccount,HttpServletRequest request) {
        // 如果不是管理员，返回空数组
         if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NOT_AUTH);
        }

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        // 判断用户是不是为空
//        if (!StringUtils.isNotBlank(userAccount)) {
//            return null;
//        }

        // 此时查到用户，但是未进行密码脱敏
//        userQueryWrapper.like("userAccount", userAccount);
        List<User> userList = userService.list(userQueryWrapper);
        List<User> result = userList.stream().map(user -> userService.gateSafetUser(user)).collect(Collectors.toList());
        return result;
//        return ResultUtils.success(result);
    }


    // 仅管理员能进行删除 (根据 id 进行删除)
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteUser(@PathVariable Long id,HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NOT_AUTH,"不是管理员权限");
        }
        // 判断 id 是否是小于0

        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"删除的id错误");
        }

        boolean result = userService.removeById(id);
        return ResultUtils.success(result);
    }


    // 判断是不是管理员（并且是不是登录状态）
    private boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(LOGIN_SUCCESS_SESSION);
        User user = (User) userObj;
        return user !=null && user.getIsAdmin() == IS_ADMIN;
    }

}
