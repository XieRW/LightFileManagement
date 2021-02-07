package com.xrw.springCloudAlibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xrw.springCloudAlibaba.config.exception.CustomSentinelBlockHandler;
import com.xrw.springCloudAlibaba.config.exception.CustomSentinelFallbackHandler;
import com.xrw.springCloudAlibaba.config.redis.RedisAutoCacheValue;
import com.xrw.springCloudAlibaba.service.FileFeignService;
import com.xrw.springCloudAlibaba.service.TestService;
import com.xrw.springCloudAlibaba.vo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
@Slf4j
@RestController
@RequestMapping("/cloud/consumer/test")
public class TestController {

    /**
     * rest接口调用模板
     */
    @Resource
    private RestTemplate restTemplate;
    /**
     * feign服务--fileFeignService
     */
    @Resource
    private FileFeignService fileFeignService;

    /**
     * 微服务名称
     */
    @Value("${service-url.nacos-service-cloud-file}")
    private String serverUrl;

    /**
     * @Description: 测试ribbon调用
     * @param : 
     * @return: com.xrw.springCloudAlibaba.vo.CommonResult<java.lang.String>
     * @Author: 谢荣旺
     * @Date: 2020/12/30
     */
    @RequestMapping("/upload")
    public CommonResult<String> upload(){
        CommonResult serverResult = restTemplate.getForObject(serverUrl+"/cloud/file/upload",CommonResult.class);
        return new CommonResult<>(serverResult.getData().toString());
    }

    /**
     * @Description: 测试feihn调用
     * @param : 
     * @return: com.xrw.springCloudAlibaba.vo.CommonResult<java.lang.String>
     * @Author: 谢荣旺
     * @Date: 2020/12/30
     */
    @RequestMapping("/uploadByFeign")
    public CommonResult<String> uploadByFeign(){
        CommonResult serverResult = fileFeignService.upload();
        return new CommonResult<>(serverResult.getData().toString());
    }

    /**
     * @Description: 测试feign超时控制
     * @param : 
     * @return: com.xrw.springCloudAlibaba.vo.CommonResult<java.lang.String>
     * @Author: 谢荣旺
     * @Date: 2020/12/30
     */
    @GetMapping(value = "/feign/timeout")
    public CommonResult<String> paymentFeignTimeout()
    {
        // OpenFeign客户端一般默认等待1秒钟
        return fileFeignService.paymentFeignTimeout();
    }

    /**
     * @Description: 测试sentinel热点key限流
     * @param p1:
     * @param p2:
     * @return: com.xrw.springCloudAlibaba.vo.CommonResult<java.lang.String>
     * @Author: 谢荣旺
     * @Date: 2020/12/30
     */
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

    /**
     * @Description: 测试sentinel限流自定义输出
     * @param p1:
     * @param p2:
     * @return: com.xrw.springCloudAlibaba.vo.CommonResult<java.lang.String>
     * @Author: 谢荣旺
     * @Date: 2020/12/30
     */
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

    /**
     * @Description: 测试sentinel限流自定义输出，使用统一处理方法的模式
     * @param p1:
     * @param p2:
     * @return: com.xrw.springCloudAlibaba.vo.CommonResult
     * @Author: 谢荣旺
     * @Date: 2020/12/30
     */
    @RequestMapping("/testSentinelResource2")
    @SentinelResource(value = "testSentinelResource2",
            blockHandlerClass = CustomSentinelBlockHandler.class,
            blockHandler = "handlerException")
    public CommonResult testSentinelResource2(@RequestParam(value = "p1",required = false) String p1,
                                                     @RequestParam(value = "p2",required = false)String p2){
        return new CommonResult("=========testSentinelResource2");
    }

    /**
     * @Description: 测试sentinel，自定义限流处理，和自定义异常处理
     * @param : 
     * @return: com.xrw.springCloudAlibaba.vo.CommonResult
     * @Author: 谢荣旺
     * @Date: 2020/12/30
     */
    @RequestMapping("/testSentinelResource3")
    @SentinelResource(value = "testSentinelResource3",
            blockHandlerClass = CustomSentinelBlockHandler.class,
            blockHandler = "handlerException",
            fallbackClass = CustomSentinelFallbackHandler.class,
            fallback = "fallbackHandle")
    public CommonResult testSentinelResource3(){
//        throw new IllegalArgumentException ("IllegalArgumentException,非法参数异常....");
        return fileFeignService.testSentinelResource3();
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
        CommonResult result = restTemplate.getForObject(serverUrl+"/cloud/file/zipkin",CommonResult.class);
        return result;
    }

    /**
     * @Description: 测试Redis查询和添加缓存，主要有value，key，condition属性
     * @param id: 
     * @return: com.xrw.springCloudAlibaba.vo.CommonResult
     * @Author: 谢荣旺
     * @Date: 2020/12/30
     */
    @Cacheable(value = RedisAutoCacheValue.DEMO,key = "#id")
    @RequestMapping("/testRedisCacheable")
    public CommonResult testRedisCacheable(@RequestParam(value = "id")Integer id){
        log.info(id.toString());
        return new CommonResult(id);
    }

    /**
     * @Description: 测试Redis更新缓存，主要有value，key，condition属性
     * @param id: 
     * @return: com.xrw.springCloudAlibaba.vo.CommonResult
     * @Author: 谢荣旺
     * @Date: 2020/12/30
     */
    @CachePut(value = RedisAutoCacheValue.DEMO,key = "#id")
    @RequestMapping("/testRedisCachePut")
    public CommonResult testRedisCachePut(@RequestParam(value = "id")Integer id){
        log.info(id.toString());
        return new CommonResult(id+1);
    }

    /**
     * @Description: 测试Redis清除缓存，主要有value，key，condition，allEntries（是否清空所有缓存内容，缺省为 false），beforeInvocation（是否在方法执行前就清空，缺省为 false）属性
     * @param id: 
     * @return: com.xrw.springCloudAlibaba.vo.CommonResult
     * @Author: 谢荣旺
     * @Date: 2020/12/30
     */
    @CacheEvict(value = RedisAutoCacheValue.DEMO,key = "#id")
    @RequestMapping("/testRedisCacheEvict")
    public CommonResult testRedisCacheEvict(@RequestParam(value = "id")Integer id){
        log.info(id.toString());
        return new CommonResult(id);
    }

    /**
     * 另外，@CacheConfig和@Caching也是常用注解
     * @CacheConfig("xxx") 用于添加在类上面，标注该类统一使用某个缓存空间
     * @Caching 用于多个Cache注解组合,包含了cacheable，put，evict属性。如：
     @Caching(put = {
     @CachePut(value = "user", key = "#user.id"),
     @CachePut(value = "user", key = "#user.username"),
     @CachePut(value = "user", key = "#user.email")
     })
     public User save(User user) {}
     */
}
