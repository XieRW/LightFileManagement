package com.xrw.springCloudAlibaba.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xrw.springCloudAlibaba.dao.UserFriendDao;
import com.xrw.springCloudAlibaba.entity.UserFriendEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description: 好友表服务层
 * @Author: xearin
 * @Date: 2021-11-12
 */
@Slf4j
@Service("UserFriendServiceImpl")
public class UserFriendServiceImpl extends ServiceImpl<UserFriendDao, UserFriendEntity> {

}