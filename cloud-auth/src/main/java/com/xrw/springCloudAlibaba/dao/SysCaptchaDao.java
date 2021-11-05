
package com.xrw.springCloudAlibaba.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xrw.springCloudAlibaba.entity.SysCaptchaEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 验证码
 *
 * @author xearin
 */
@Mapper
public interface SysCaptchaDao extends BaseMapper<SysCaptchaEntity> {

}
