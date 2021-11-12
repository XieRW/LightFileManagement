package com.xrw.springCloudAlibaba.dto;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @Description: 用户信息，和com.xrw.springCloudAlibaba.entity.SysUserEntity类保持字段同步
 * @Author: xearin
 * @Date: 2021/11/12
 */
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "exception"})
public class SysUser {

    private Long id;
    private Integer revision;
    private Date createTime;
    private Date updateTime;
    private Long createUserId;
    private Long updateUserId;
    private Integer isDeleted;
    private String username;
    private String name;
    private String password;
    private String salt;
    private String email;
    private String mobile;
    private Long roleId;
    private String roleName;
    private String sourceUserId;
    private String sourceType;
    private Integer status;
    private String token;
}