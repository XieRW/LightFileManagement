package com.xrw.springCloudAlibaba.Controller;

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
@RequestMapping("/cloud/consumer")
public class TestController {


    /**
     * @Description: 测试类
     * @param s:
     * @param a:
     * @return: void
     * @Author: 谢荣旺
     * @Date: 2020/12/14
     */
    @RequestMapping("/ss")
    public void test(@RequestParam("ss")String s,@RequestParam("ss")String a){
        return;
    }

}
