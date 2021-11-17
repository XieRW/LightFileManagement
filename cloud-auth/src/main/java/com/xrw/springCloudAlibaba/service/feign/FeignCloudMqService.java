package com.xrw.springCloudAlibaba.service.feign;

import com.xrw.springCloudAlibaba.config.feign.FeignHeadConfig;
import com.xrw.springCloudAlibaba.service.feign.fallback.FeignCloudMqServiceFallbackImpl;
import com.xrw.springCloudAlibaba.vo.ResponseJSON;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: 使用openfeign远程调用cloudMq服务
 * @Author: xearin
 * @Date: 2021/11/17
 */
@Service
@FeignClient(value = "cloud-mq/mq",configuration = FeignHeadConfig.class,fallback = FeignCloudMqServiceFallbackImpl.class)
public interface FeignCloudMqService {

    @RequestMapping(value = "/rocket/sendMessage",method = RequestMethod.POST)
    ResponseJSON sendMsg(@RequestParam(value = "topic",required = false) String topic,
                         @RequestParam(value = "tags",required = false) String tags,
                         @RequestParam(value = "msg") String msg);
}
