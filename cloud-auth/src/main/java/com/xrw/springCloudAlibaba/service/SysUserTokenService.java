

package com.xrw.springCloudAlibaba.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xrw.springCloudAlibaba.entity.SysUserEntity;
import com.xrw.springCloudAlibaba.entity.SysUserTokenEntity;

/**
 * 用户Token
 *
 * @author admin
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

	/**
	 * time为-1时,时间是7天,否则是1年
	 * @param contactorId
	 * @param tokenType
	 * @param time
	 * @return
	 */
	String createWxToken(long contactorId, String tokenType,int  time);

	/**
	 * 生成token
	 * @param userId
	 * @param tokenType
	 * @return
	 */
	String createToken(long userId,String tokenType);
}
