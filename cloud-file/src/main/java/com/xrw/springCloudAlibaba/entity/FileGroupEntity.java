package com.xrw.springCloudAlibaba.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

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
    @JSONField(name = "pId")
    private Long pId;

    public Long getpId() {
        return pId;
    }

    public FileGroupEntity setpId(Long pId) {
        this.pId = pId;
        return this;
    }

    /**
     * 分组名
     */
    private String name;
    /**
     * 分组说明
     */
    private String detail;
    /**
     * 用户id
     */
    private Long userId;

    @TableField(exist = false)
    private List<FileGroupEntity> children;
}
