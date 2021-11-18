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
 * @description: 文件表
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-11-12 09:43
 **/
@Data
@Accessors(chain = true)
@TableName(value = "file")
public class FileEntity {
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
     * 文件大小,单位：byte
     */
    private Integer size;
    /**
     * 文件实际名称
     */
    private String filename;
    /**
     * 文件URL
     */
    private String url;
    /**
     * 文件物理存储相对路径
     */
    private String path;
    /**
     * 物理存储文件名称
     */
    private String storename;
    /**
     * 文件扩展名
     */
    private String extension;
    /**
     * 文件备注
     */
    private String remark;
    /**
     * 文件分类目录
     */
    private String directory;
    /**
     * 文件分组
     */
    private Long fileGroupId;
}
