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

    private Integer errorcode;
    private String  message;
    private T       data;

    public CommonResult(Integer errorcode,String message){
        this(errorcode,message,null);
    }

    public CommonResult(T data){
        this(200,"success",data);
    }

}
