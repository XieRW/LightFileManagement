package com.xrw.springCloudAlibaba.service;

import com.xrw.springCloudAlibaba.vo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 文件服务调用接口,by openFeign
 * @return:
 * @Author: 谢荣旺
 * @Date: 2020/12/21
 */
@Service
@FeignClient(value = "cloud-file")
public interface FileFeignService {

    /**
     * @Description: 测试类
     * @return: void
     * @Author: 谢荣旺
     * @Date: 2020/12/14
     */
    @RequestMapping("/cloud/file/upload")
    public CommonResult<String> upload();

}
