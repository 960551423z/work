package com.zq.demo.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private String sex;

    /**
     * 是否是管理员 0 - 普通用户  1 - 管理员 
     */
    private Integer isAdmin;

    /**
     * 用户是否禁用  0 - 不被禁用 1 - 被禁用
     */
    private Integer isUse;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除  0 - 未删除 1 - 已删除 
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 头像
     */
    private String imgUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}