package com.xrw.springCloudAlibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xrw.springCloudAlibaba.config.exception.CustomSentinelBlockHandler;
import com.xrw.springCloudAlibaba.config.exception.CustomSentinelFallbackHandler;
//import com.xrw.springCloudAlibaba.service.FileFeignService;
import com.xrw.springCloudAlibaba.service.TestService;
import com.xrw.springCloudAlibaba.vo.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
//    @Resource
//    private FileFeignService fileFeignService;
    @Resource
    private TestService testService;

    @Value("${service-url.nacos-service-cloud-file}")
    private String serverUrl;

    @RequestMapping("/upload")
    public CommonResult<String> upload(){
        CommonResult serverResult = restTemplate.getForObject(serverUrl+"/cloud/file/upload",CommonResult.class);
        return new CommonResult<>(serverResult.getData().toString());
    }

//    @RequestMapping("/uploadByFeign")
//    public CommonResult<String> uploadByFeign(){
//        CommonResult serverResult = fileFeignService.upload();
//        return new CommonResult<>(serverResult.getData().toString());
//    }

//    @GetMapping(value = "/feign/timeout")
//    public CommonResult<String> paymentFeignTimeout()
//    {
//        // OpenFeign客户端一般默认等待1秒钟
//        return fileFeignService.paymentFeignTimeout();
//    }

    @RequestMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")
    public CommonResult<String> testHotKey(@RequestParam(value = "p1",required = false) String p1,
                                           @RequestParam(value = "p2",required = false)String p2){
//        int age = 10/0;
        return new CommonResult("=========testHotKey");
    }
    public CommonResult<String> deal_testHotKey(String p1, String p2, BlockException e){
        return new CommonResult("=========deal_testHotKey,o(╥﹏╥)o");
    }

    @RequestMapping("/testSentinelResource")
    @SentinelResource(value = "testSentinelResource",blockHandler = "handle_testSentinelResource")
    public CommonResult<String> testSentinelResource(@RequestParam(value = "p1",required = false) String p1,
                                           @RequestParam(value = "p2",required = false)String p2){
//        int age = 10/0;
        return new CommonResult("=========testSentinelResource");
    }
    public CommonResult<String> handle_testSentinelResource(String p1, String p2, BlockException e){
        return new CommonResult(444,e.getClass().getCanonicalName()+"服务不可用");
    }

    @RequestMapping("/testSentinelResource2")
    @SentinelResource(value = "testSentinelResource2",
            blockHandlerClass = CustomSentinelBlockHandler.class,
            blockHandler = "handlerException")
    public CommonResult testSentinelResource2(@RequestParam(value = "p1",required = false) String p1,
                                                     @RequestParam(value = "p2",required = false)String p2){
        return new CommonResult("=========testSentinelResource2");
    }

    @RequestMapping("/testSentinelResource3")
    @SentinelResource(value = "testSentinelResource3",
            blockHandlerClass = CustomSentinelBlockHandler.class,
            blockHandler = "handlerException",
            fallbackClass = CustomSentinelFallbackHandler.class,
            fallback = "fallbackHandle")
    public CommonResult testSentinelResource3(){
//        throw new IllegalArgumentException ("IllegalArgumentException,非法参数异常....");
        return testService.testSentinelResource3();
    }

}
