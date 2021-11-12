package com.xrw.springCloudAlibaba.controller;

import com.xrw.springCloudAlibaba.service.impl.UserFriendApplicationServiceImpl;
import com.xrw.springCloudAlibaba.service.impl.UserFriendServiceImpl;
import com.xrw.springCloudAlibaba.vo.ResponseJSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: LightFileManagement
 * @description: 用户好友控制器层
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-11-12 10:30
 **/
@Slf4j
@RestController
@RequestMapping("/user/friend")
public class UserFriendController {
    @Autowired
    private UserFriendServiceImpl userFriendService;
    @Autowired
    private UserFriendApplicationServiceImpl userFriendApplicationService;

    @RequestMapping("/page")
    public ResponseJSON page(){
        return new ResponseJSON();
    }

    @RequestMapping("/delete")
    public ResponseJSON delete(){
        return new ResponseJSON();
    }

}
