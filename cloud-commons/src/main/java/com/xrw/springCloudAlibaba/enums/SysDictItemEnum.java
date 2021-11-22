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
    file_share_permission_0("file_share_permission","0","全部"),
    file_share_permission_1("file_share_permission","1","查看"),
    file_share_permission_2("file_share_permission","2","修改"),
    file_share_permission_3("file_share_permission","3","删除"),
    /**
     * 文件类型
     */
    file_type_0("file_type","0","普通文件"),
    file_type_1("file_type","1","用户系统文件，头像等");
    private String dicKey;
    private String key;
    private String value;

    public static SysDictItemEnum getByKey(String dicKey,String key){
        SysDictItemEnum[] items = values();
        for (SysDictItemEnum item:items) {
            if (item.getDicKey().equals(dicKey)&&item.getKey().equals(key)){
                return item;
            }
        }
        return null;
    }
}
