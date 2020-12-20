package com.xrw.springCloudAlibaba.controller;

import com.xrw.springCloudAlibaba.service.FileFeignService;
import com.xrw.springCloudAlibaba.vo.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @program: LightFileManagement
 * @description: 测试类
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2020-12-21 00:06
 **/
@RestController
@RequestMapping("/cloud/consumer/test")
public class TestController {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private FileFeignService fileFeignService;

    @Value("${service-url.nacos-service-cloud-file}")
    private String serverUrl;

    @RequestMapping("/upload")
    public CommonResult<String> upload(){
        CommonResult serverResult = restTemplate.getForObject(serverUrl+"/cloud/file/upload",CommonResult.class);
        return new CommonResult<>(serverResult.getData().toString());
    }

    @RequestMapping("/uploadByFeign")
    public CommonResult<String> uploadByFeign(){
        CommonResult serverResult = fileFeignService.upload();
        return new CommonResult<>(serverResult.getData().toString());
    }

    @GetMapping(value = "/feign/timeout")
    public CommonResult<String> paymentFeignTimeout()
    {
        // OpenFeign客户端一般默认等待1秒钟
        return fileFeignService.paymentFeignTimeout();
    }
}
