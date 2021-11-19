package com.xrw.springCloudAlibaba.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @program: LightFileManagement
 * @description: 文件分组表
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-11-12 09:43
 **/
@Data
@Accessors(chain = true)
@TableName(value = "file_group")
public class FileGroupEntity {
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
     * 父分组id
     */
    private Long pId;
    /**
     * 分组名
     */
    private String name;
    /**
     * 分组说明
     */
    private String detail;
}
