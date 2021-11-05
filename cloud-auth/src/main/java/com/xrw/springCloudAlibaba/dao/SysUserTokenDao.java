
package com.xrw.springCloudAlibaba.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xrw.springCloudAlibaba.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Token
 *
 * @author xearin
 */
@Mapper
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

    SysUserTokenEntity queryByToken(String token);

}
