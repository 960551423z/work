create database demo;

use demo;

create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    userAccount  varchar(256)                                                                                        null comment '用户名',
    userPassword varchar(256)                                                                                        null comment '密码',
    phone        varchar(128)                                                                                        null comment '手机号',
    email        varchar(256)                                                                                        null comment '邮箱',
    sex          varchar(100)                                                                                        null comment '性别',
    isAdmin      int           default 0                                                                             not null comment '是否是管理员 0 - 普通用户  1 - 管理员 ',
    isUse        int           default 0                                                                             not null comment '用户是否禁用  0 - 不被禁用 1 - 被禁用',
    createTime   datetime      default CURRENT_TIMESTAMP                                                             null comment '创建时间',
    updateTime   datetime      default CURRENT_TIMESTAMP                                                             null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     int           default 0                                                                             not null comment '逻辑删除  0 - 未删除 1 - 已删除 ',
    imgUrl       varchar(1024) default 'https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/3499/2.png' not null comment '头像'
)
    comment '用户';