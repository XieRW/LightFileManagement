package com.xrw.springCloudAlibaba.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xrw.springCloudAlibaba.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author leon
 * @email 
 * @date 2021-04-2 14:00:58
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {
    /**
     * 根据用户名，查询系统用户
     * @param username
     */
    SysUserEntity queryByUserName(String username);

    SysUserEntity listBy(@Param("username") String username, @Param("name") String name, @Param("mobile") String mobile,
                         @Param("sourceUserId") String sourceUserId, @Param("sourceType") String sourceType, @Param("platForm") Long platForm);

    List<SysUserEntity> listByUsernameOrMobile(@Param("mobile") String mobile, @Param("username") String username);
}
