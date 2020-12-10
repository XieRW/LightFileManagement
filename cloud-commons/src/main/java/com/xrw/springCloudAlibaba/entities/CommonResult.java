package com.xrw.springCloudAlibaba.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息返回工具类
 *
 * @author xrw 2020-12-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T>  {

    private Integer code;
    private String  message;
    private T       data;

    public CommonResult(Integer code,String message){
        this(code,message,null);
    }
}
