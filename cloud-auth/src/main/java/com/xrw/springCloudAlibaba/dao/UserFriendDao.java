package com.xrw.springCloudAlibaba.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xrw.springCloudAlibaba.entity.UserFriendEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xearin
 * @email 
 * @date 2021-11-12 14:00:58
 */
@Mapper
public interface UserFriendDao extends BaseMapper<UserFriendEntity> {

}
