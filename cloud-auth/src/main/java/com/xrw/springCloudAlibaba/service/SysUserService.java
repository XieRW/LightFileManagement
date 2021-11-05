package com.xrw.springCloudAlibaba.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xrw.springCloudAlibaba.entity.SysUserEntity;

/**
 * @author xearin
 * @email
 * @date 2021-04-2 14:00:58
 */
public interface SysUserService extends IService<SysUserEntity> {

    SysUserEntity getUserByName(String name);

    /**
     * 根据用户名，查询系统用户
     */
    SysUserEntity queryByUserName(String username);

    SysUserEntity listBy(String username, String name, String mobile, String sourceUserId, String sourceType, Long platForm);

    Boolean listByUsernamOrMobile(String username, String mobile, Long userId);
}

