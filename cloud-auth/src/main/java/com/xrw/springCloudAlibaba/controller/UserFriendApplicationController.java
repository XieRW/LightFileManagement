package com.xrw.springCloudAlibaba.controller;

import com.xrw.springCloudAlibaba.entity.UserFriendApplicationEntity;
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
 * @description: 用户好友申请控制器层
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

    /**
     * @Description: 新增好友请求记录
     * @param applyToId: 想要添加的好友的id
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin
     * @Date: 2021/11/12
     */
    @RequestMapping("/add")
    public ResponseJSON add(@RequestParam(value = "applyToId")Long applyToId){
        UserFriendApplicationEntity userFriendApplicationEntity = userFriendApplicationService.addById(applyToId);
        return new ResponseJSON(userFriendApplicationEntity);
    }

    @RequestMapping("/page")
    public ResponseJSON page(){
        return new ResponseJSON();
    }

    /**
     * @Description: 处置好友请求记录
     * @param id: 记录id
     * @param dispose: 处置类型
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin
     * @Date: 2021/11/12
     */
    @RequestMapping("/dispose")
    public ResponseJSON dispose(@RequestParam(value = "id")Long id,@RequestParam(value = "dispose")String dispose){
        userFriendApplicationService.dispose(id,dispose);
        return new ResponseJSON();
    }
}
