package com.xrw.springCloudAlibaba.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xrw.springCloudAlibaba.entity.UserFriendEntity;
import com.xrw.springCloudAlibaba.service.UserFriendApplicationServiceImpl;
import com.xrw.springCloudAlibaba.service.UserFriendServiceImpl;
import com.xrw.springCloudAlibaba.utils.login.LoginUserHolder;
import com.xrw.springCloudAlibaba.vo.ResponseJSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * @Description: 分页查询好友，并支持对用户名、昵称、电话号码、邮箱的模糊查询
     * @param page: 页码，从1开始
     * @param size: 每页数量
     * @param select: 模糊查询关键字
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin
     * @Date: 2021/11/17
     */
    @RequestMapping("/page")
    public ResponseJSON page(@RequestParam(value = "page",required = false)Integer page,
                             @RequestParam(value = "size",required = false)Integer size,
                             @RequestParam(value = "String",required = false,defaultValue = "")String select){
        Page<UserFriendEntity> entityPage = userFriendService.pageSelect(LoginUserHolder.getUserId(), select, page, size);
        return new ResponseJSON(entityPage);
    }

    @RequestMapping("/delete")
    public ResponseJSON delete(){
        return new ResponseJSON();
    }



}
