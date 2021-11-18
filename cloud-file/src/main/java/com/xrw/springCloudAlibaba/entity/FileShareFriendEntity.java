package com.xrw.springCloudAlibaba.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @program: LightFileManagement
 * @description: 文件共享表
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-11-12 09:43
 **/
@Data
@Accessors(chain = true)
@TableName(value = "file_share_friend")
public class FileShareFriendEntity {
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
     * 共享权限
     */
    private String fileSharePermission;
    /**
     * 共享来源
     */
    private Long shareFrom;
    /**
     * 共享目标
     */
    private Long shareTo;
    /**
     * 文件id
     */
    private Long fileId;
}
