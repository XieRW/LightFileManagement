package com.xrw.springCloudAlibaba.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息返回工具类
 *
 * @author xearin 2020-12-10
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

    public CommonResult(T data){
        this(200,"success",data);
    }

}
