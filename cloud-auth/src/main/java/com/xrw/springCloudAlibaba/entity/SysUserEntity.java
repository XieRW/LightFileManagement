

package com.xrw.springCloudAlibaba.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * TODO 系统用户
 * @author zhicaili
 * @date 2019-05-22 14:54:50
 */
@Data
@TableName("user")
public class SysUserEntity extends Model<SysUserEntity> {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId
	private Long id;
	private Integer revision;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	private Long createUserId;
	private Long updateUserId;
	@TableLogic
	private Integer isDeleted;

	/**
	 * 用户登录名
	 */
	private String username;
	/**
	 * 用户昵称
	 */
	private String name;

	/**
	 * 密码
	 */
	@JSONField(serialize = false)
	private String password;

	/**
	 * 盐
	 */
	@JSONField(serialize = false)
	private String salt;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 角色ID
	 */
	private Long roleId;
	@TableField(exist=false)
	private String roleName;
	@TableField(exist=false)
	private String sourceUserId;
	private String sourceType;
	/**
	 * 状态  0：禁用   1：正常  2：
	 */
	@JSONField(serialize = false)
	private Integer status;
	@TableField(exist=false)
	private String token;
}
