package com.xrw.springCloudAlibaba.dto;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @program: LightFileManagement
 * @description: 发送消息的消息体
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-02-07 18:46
 **/
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@Getter
@Setter
public class User {
    private String id;
    private String username;
    private String pwd;
}
