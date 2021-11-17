package com.xrw.springCloudAlibaba.service.feign.fallback;

import com.xrw.springCloudAlibaba.exception.ApiError;
import com.xrw.springCloudAlibaba.exception.ApiException;
import com.xrw.springCloudAlibaba.service.feign.FeignCloudMqService;
import com.xrw.springCloudAlibaba.vo.ResponseJSON;
import org.springframework.stereotype.Component;

/**
 * @program: LightFileManagement
 * @description: cloudmq服务openfeign调用fallback处理
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-11-17 10:17
 **/
@Component
public class FeignCloudMqServiceFallbackImpl implements FeignCloudMqService {
    @Override
    public ResponseJSON sendMsg(String topic, String tags, String msg) {
        throw new ApiException(ApiError.FEIGN_CLOUD_MQ_ERROR);
    }
}
