package com.xrw.springCloudAlibaba.config.exception;

import com.xrw.springCloudAlibaba.vo.CommonResult;

/**
 * @program: LightFileManagement
 * @description: 自定义的异常处理类
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2020-12-28 17:56
 **/
public class CustomSentinelFallbackHandler {
    public static CommonResult fallbackHandle(Throwable e){
        return new CommonResult(444,"兜底异常handlerFallback,exception内容:  "+e.getMessage());
    }
}
