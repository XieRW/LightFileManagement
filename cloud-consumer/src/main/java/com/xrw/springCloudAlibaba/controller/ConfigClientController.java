package com.xrw.springCloudAlibaba.controller;

import com.xrw.springCloudAlibaba.vo.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @program: LightFileManagement
 * @description: Nacos配置中心统一配置返回接口
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2020-12-21 01:28
 **/
@RestController
@RefreshScope //支持Nacos的动态刷新功能。
@RequestMapping("/cloud/consumer/config")
public class ConfigClientController {

    @Value("${config.info.version}")
    private String version;

    @RequestMapping("/getVersion")
    public CommonResult getVersion(){
        return new CommonResult(Optional.ofNullable(version));
    }
}
