package com.xrw.springCloudAlibaba.Controller;

import com.xrw.springCloudAlibaba.vo.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: LightFileManagement
 * @description: 测试一下接口
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2020-12-14 22:11
 **/
@RestController
@RequestMapping("/cloud/file")
public class TestController {

    @Value("${server.port}")
    String port;

    /**
     * @Description: 测试类
     * @return: void
     * @Author: 谢荣旺
     * @Date: 2020/12/14
     */
    @RequestMapping("/upload")
    public CommonResult<String> upload(){
        return new CommonResult<String>("cloud-file:"+port+"/cloud/consumer/upload"+"调用成功！");
    }

}
