

package com.xrw.springCloudAlibaba.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xrw.springCloudAlibaba.entity.SysCaptchaEntity;

import java.awt.image.BufferedImage;

/**
 * 验证码
 *
 * @author xearin
 */
public interface SysCaptchaService extends IService<SysCaptchaEntity> {

    /**
     * 获取图片验证码
     */
    BufferedImage getCaptcha(String uuid);

    /**
     * 验证码效验
     * @param uuid  uuid
     * @param errorcode  验证码
     * @return  true：成功  false：失败
     */
    boolean validate(String uuid, String errorcode);
}
