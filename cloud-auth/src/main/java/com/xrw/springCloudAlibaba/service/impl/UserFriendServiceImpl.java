package com.xrw.springCloudAlibaba.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xrw.springCloudAlibaba.dao.UserFriendDao;
import com.xrw.springCloudAlibaba.entity.SysUserEntity;
import com.xrw.springCloudAlibaba.entity.UserFriendEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 好友表服务层
 * @Author: xearin
 * @Date: 2021-11-12
 */
@Slf4j
@Service("UserFriendServiceImpl")
public class UserFriendServiceImpl extends ServiceImpl<UserFriendDao, UserFriendEntity> {

}