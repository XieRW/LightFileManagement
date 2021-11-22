package com.xrw.springCloudAlibaba.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description: 字典表
 * @Author: xearin
 * @Date: 2021/11/12
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SysDictEnum {
    /**
     * 处置状态
     */
    apply_status("apply_status","处置状态"),
    /**
     * 文件共享权限
     */
    file_share_permission("file_share_permission","文件共享权限"),
    /**
     * 文件类型
     */
    file_type("file_type","文件类型");

    private String key;
    private String value;
}
