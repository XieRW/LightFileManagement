

package com.xrw.springCloudAlibaba.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * TODO 系统用户
 * @author xearin
 * @date 2019-05-22 14:54:50
 */
@Data
@TableName("role")
public class SysRoleEntity extends Model<SysRoleEntity> {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId
	private Long id;
	@TableLogic
	private Integer isDeleted;
	private Integer revision;
	private Long createUserId;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	private Long updateUserId;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	private String name;
	private String remark;
}
