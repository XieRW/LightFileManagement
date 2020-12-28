package com.xrw.springCloudAlibaba.service;

import com.xrw.springCloudAlibaba.vo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: LightFileManagement
 * @description: 测试接口的异常处理实现类
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2020-12-28 18:48
 **/
@Component
public class TestFallbackService implements FileFeignService{

    @Override
    public CommonResult<String> upload() {
        return new CommonResult<>(44444,"服务降级返回,---TestFallbackService");
    }

    @Override
    public CommonResult<String> paymentFeignTimeout() {
        return new CommonResult<>(44444,"服务降级返回,---TestFallbackService");
    }

    @Override
    public CommonResult testSentinelResource3() {
        return new CommonResult<>(44444,"服务降级返回,---TestFallbackService");
    }
}
