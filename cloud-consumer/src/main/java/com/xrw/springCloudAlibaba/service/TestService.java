package com.xrw.springCloudAlibaba.service;

import com.xrw.springCloudAlibaba.vo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 测试接口,by openFeign
 * @return:
 * @Author: 谢荣旺 1429382875@qq.com
 * @Date: 2020/12/28
 */
@FeignClient(value = "cloud-file",fallback = TestFallbackService.class)
public interface TestService {

    @RequestMapping("/cloud/file/testSentinelResource3")
    public CommonResult testSentinelResource3();
}
