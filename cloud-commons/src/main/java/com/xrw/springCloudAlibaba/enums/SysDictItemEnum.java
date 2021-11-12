package com.xrw.springCloudAlibaba.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SysDictItemEnum {
    /**
     * 处置状态
     */
    apply_status_0("apply_status","0","未处置"),
    apply_status_1("apply_status","1","已通过"),
    apply_status_2("apply_status","2","已拒绝"),
    /**
     * 文件共享权限
     */
    file_share_permission_0("file_share_permission","0","文件共享权限"),
    file_share_permission_1("file_share_permission","1","文件共享权限"),
    file_share_permission_2("file_share_permission","2","文件共享权限"),
    file_share_permission_3("file_share_permission","3","文件共享权限");

    private String dicKey;
    private String key;
    private String value;

    public static SysDictItemEnum getByKey(String key){
        SysDictItemEnum[] items = values();
        for (SysDictItemEnum item:items) {
            if (item.getKey().equals(key)){
                return item;
            }
        }
        return null;
    }
}
