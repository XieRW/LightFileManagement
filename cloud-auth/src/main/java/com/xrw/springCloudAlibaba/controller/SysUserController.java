package com.xrw.springCloudAlibaba.controller;

import com.xrw.springCloudAlibaba.entity.SysUserEntity;
import com.xrw.springCloudAlibaba.service.SysUserServiceImpl;
import com.xrw.springCloudAlibaba.vo.ResponseJSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: LightFileManagement
 * @description: 系统用户控制器层
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-11-15 10:03
 **/
@RestController
@RequestMapping("/user")
@Slf4j
public class SysUserController {
    @Autowired
    private SysUserServiceImpl userService;

    @RequestMapping("select/perfectMatching")
    public ResponseJSON perfectMatching(@RequestParam(value = "search")String search){
        List<SysUserEntity> sysUserEntities = userService.perfectMatching(search);
        return new ResponseJSON(sysUserEntities);
    }
}
