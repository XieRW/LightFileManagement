package com.xrw.springCloudAlibaba.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 联系人表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-25 13:33:40
 */
@Data
@TableName("mail_contactor")
public class MailContactorEntity extends Model<MailContactorEntity> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 联系人id
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
	 * 联系人名称
	 */
	private String name;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 工作单位
	 */
	private String workUnit;
	/**
	 * 职务
	 */

	private String position;
	/**
	 * 是否重要联系人 0：否  1：是
	 */

	@TableField("is_importance")
	private Integer importance;
	/**
	 * 办公室号码
	 */
	private String officeTel;
	/**
	 * 手机号码1
	 */
	private String mobile1;
	/**
	 * 手机号码2
	 */
	private String mobile2;
	/**
	 * 家庭号码
	 */
	private String homeTel;
	/**
	 * 传真号码
	 */
	private String fax;
	/**
	 * 其他号码
	 */
	private String otherTel;
	/**
	 * 是否常用联系人 0：否  1：是
	 */
	@TableField("is_commonly_used")
	private Integer commonlyUsed;
	/**
	 * 拼音首字母
	 */
	private String firstName;
	/**
	 * 邮箱地址
	 */
	private String email;
	/**
	 * 终端用户id
	 */
	private Long platformId;
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 经度
	 */
	private Double longitude;
	/**
	 * 维度
	 */
	private Double latitude;
	/**
	 * 微信id
	 */
	private String openId;

	/**
	 * 地址
	 */
	private String address;

	/**
	 *用户标识，普通用户为1
	 */
	private int contactorFlag;


	/**
	 *短信可再次提醒的时间
	 */
	private Date smsPromptExpiredTime;

	private Integer appUser;

	/**
	 * xls导入时的分组
	 */
	@TableField(exist=false)
	private String group;

	/**
	 * 保存时所存在分组的id
	 */
	@TableField(exist=false)
	private List<Long> groups;

	/**
	 * 要加入的分组id
	 */
	@TableField(exist=false)
	private Long groupId;

	/**
	 * 保存导入时出现的错误信息
	 */
	@TableField(exist=false)
	private StringBuilder errorMsg;

	/**
	 * 验证码
	 */
	@TableField(exist=false)
	private String verificationCode;


	/**
	 * 分组验证码
	 */
	@TableField(exist=false)
	private String groupVerificationCode;

	/**
	 * 保存的分组名称
	 */
	@TableField(exist=false)
	private String groupName;


	public void setErrorMsg(String msg){
		if(this.errorMsg ==null){
			this.errorMsg=new StringBuilder();
		}
		this.errorMsg.append(msg);
		//this.errorMsg.append(",");
	}

	/**
	 *紧急联系人id
	 */
	private Long urgentContactorId;



	@TableField(exist=false)
	private String urgentContactorName;

	@TableField(exist=false)
	private String urgentContactorMobile1;

	@TableField(exist=false)
	private String phone;
	/**
	 * personnelMark
	 */
	private Integer personnelMark;


	private String images;

	private Integer orderNum;
	/**
	 * 默认号码类型（0手机号码  1办公号码 2家庭号码 3其他号码）
	 */
	private Integer defaultPhoneType;
	@TableField(exist=false)
	private String contactType;


	/**
	 * 是否微信用户，1是  0否
	 */
	private Integer wxUser;
	
	
	@TableField(exist=false)
	private int messageUpCount;
	
	@TableField(exist=false)
	private int receiveCount;

	/**
	 * 随机密码
	 */
	private String randomPassword;
	

	@TableField(exist=false)
	private String token;

	@TableField(exist=false)
	private Long roleId;
	@TableField(exist=false)
	/**
	 *预案发送短信识别id
	 */
	private Long identifyId;
	@TableField(exist=false)
	/**
	 *预案发送短信人所属职务职责
	 */
	private String responsibilities;

	@TableField(exist=false)
	private Object resources;

	private String source_contact_id;

	private String source_type;
}
