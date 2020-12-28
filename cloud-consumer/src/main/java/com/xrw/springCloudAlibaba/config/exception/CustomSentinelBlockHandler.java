package com.xrw.springCloudAlibaba.config.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xrw.springCloudAlibaba.vo.CommonResult;

/**
 * @program: LightFileManagement
 * @description: 自定义的sentinel异常处理类
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2020-12-28 16:39
 **/
public class CustomSentinelBlockHandler {
    public static CommonResult handlerException(BlockException exception){
        return new CommonResult(4444,"自定义global handlerException----");
    }
}
