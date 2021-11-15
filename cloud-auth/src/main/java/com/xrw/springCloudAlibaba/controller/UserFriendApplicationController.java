package com.xrw.springCloudAlibaba.controller;

import com.xrw.springCloudAlibaba.entity.UserFriendApplicationEntity;
import com.xrw.springCloudAlibaba.service.UserFriendApplicationServiceImpl;
import com.xrw.springCloudAlibaba.service.UserFriendServiceImpl;
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
    /**
     * Field injection is not recommended
     * 此提示的意思是，最好是用构造函数的方式注入bean，目的是为了显示的声明bean注入的顺序，避免在bean未被创建前使用bean的属性
     */
    @Autowired
    private UserFriendServiceImpl userFriendService;
    @Autowired
    private UserFriendApplicationServiceImpl userFriendApplicationService;

    /**
     * @param applyToId: 想要添加的好友的id
     * @Description: 新增好友请求记录
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin
     * @Date: 2021/11/12
     */
    @RequestMapping("/add")
    public ResponseJSON add(@RequestParam(value = "applyToId") Long applyToId) {
        UserFriendApplicationEntity userFriendApplicationEntity = userFriendApplicationService.addById(applyToId);
        return new ResponseJSON(userFriendApplicationEntity);
    }

    /**
     * @param :
     * @Description: page
     * 暂未使用
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin
     * @Date: 2021/11/12
     */
    @RequestMapping("/page")
    public ResponseJSON page() {
        return new ResponseJSON();
    }

    /**
     * @param id:      记录id
     * @param dispose: 处置类型
     * @Description: 处置好友请求记录
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin
     * @Date: 2021/11/12
     */
    @RequestMapping("/dispose")
    public ResponseJSON dispose(@RequestParam(value = "id") Long id, @RequestParam(value = "dispose") String dispose) {
        userFriendApplicationService.dispose(id, dispose);
        return new ResponseJSON();
    }
}
