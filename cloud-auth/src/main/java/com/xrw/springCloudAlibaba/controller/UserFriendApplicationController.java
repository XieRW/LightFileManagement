package com.xrw.springCloudAlibaba.controller;

import com.xrw.springCloudAlibaba.service.impl.UserFriendApplicationServiceImpl;
import com.xrw.springCloudAlibaba.service.impl.UserFriendServiceImpl;
import com.xrw.springCloudAlibaba.vo.ResponseJSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: LightFileManagement
 * @description: 用户好友事情控制器层
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-11-12 10:30
 **/
@Slf4j
@RestController
@RequestMapping("/user/friend/application")
public class UserFriendApplicationController {
    @Autowired
    private UserFriendServiceImpl userFriendService;
    @Autowired
    private UserFriendApplicationServiceImpl userFriendApplicationService;

    @RequestMapping("/add")
    public ResponseJSON add(@RequestParam(value = "applyToId")Long applyToId){

        return new ResponseJSON();
    }

    @RequestMapping("/page")
    public ResponseJSON page(){
        return new ResponseJSON();
    }

    @RequestMapping("/dispose")
    public ResponseJSON dispose(){
        return new ResponseJSON();
    }
}
