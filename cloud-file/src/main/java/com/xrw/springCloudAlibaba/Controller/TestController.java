package com.xrw.springCloudAlibaba.Controller;

import com.xrw.springCloudAlibaba.vo.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

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

    /**
     * @Description: paymentFeignTimeout 超时控制
     * @param :
     * @return: java.lang.String
     * @Author: 谢荣旺
     * @Date: 2020/12/21
     */
    @GetMapping(value = "/feign/timeout")
    public CommonResult<String> paymentFeignTimeout()
    {
        // 业务逻辑处理正确，但是需要耗费3秒钟
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        return new CommonResult<String>("cloud-file:"+port+"/cloud/consumer/feign/timeout"+"调用成功！");
    }

    /**
     * @Description: testSentinelResource3
     * @param :
     * @return: com.xrw.springCloudAlibaba.vo.CommonResult
     * @Author: 谢荣旺
     * @Date: 2020/12/28
     */
    @RequestMapping("/testSentinelResource3")
    public CommonResult testSentinelResource3(){
//        throw new IllegalArgumentException ("IllegalArgumentException,非法参数异常....");
        return new CommonResult("=========testSentinelResource3");
    }

    /**
     * @Description: 测试zipkin链路追踪
     * @param :
     * @return: java.lang.String
     * @Author: 谢荣旺
     * @Date: 2020/12/28
     */
    @GetMapping("/zipkin")
    public CommonResult cloudFileZipkin()
    {
        return new CommonResult("hi ,i'am cloudFileZipkin server fall back，welcome to cloudXRW，O(∩_∩)O哈哈~");
    }

}
