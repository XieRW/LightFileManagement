package com.xrw.springCloudAlibaba.controller;

import com.xrw.springCloudAlibaba.dto.User;
import com.xrw.springCloudAlibaba.entity.SysUserEntity;
import com.xrw.springCloudAlibaba.exception.ApiError;
import com.xrw.springCloudAlibaba.exception.ApiException;
import com.xrw.springCloudAlibaba.service.SysCaptchaService;
import com.xrw.springCloudAlibaba.service.SysUserService;
import com.xrw.springCloudAlibaba.service.impl.CustomUserDetailsServiceImpl;
import com.xrw.springCloudAlibaba.utils.AESUtil;
import com.xrw.springCloudAlibaba.vo.ResponseJSON;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @program: LightFileManagement
 * @description: 系统登录控制器
 * @author: xearin-谢荣旺 1429382875@qq.com
 * @create: 2021-11-04 10:51
 **/
@RestController
public class LoginController {
    @Autowired
    private SysCaptchaService sysCaptchaService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 验证码
     */
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //获取图片验证码
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * hello
     */
    @GetMapping("/test/hello")
    public ResponseJSON hello(HttpServletResponse response, String uuid) throws IOException {
        return new ResponseJSON("hello");
    }

    /**
     * @Description: 普通用户注册
     * @param captcha: 验证码
     * @param uuid: uuid
     * @param username: 用户名
     * @param password: 密码
     * @param email: 邮箱
     * @return: com.xrw.springCloudAlibaba.vo.ResponseJSON
     * @Author: xearin
     * @Date: 2021/11/6
     */
    @RequestMapping("/permit/signUp")
    public ResponseJSON signUp(@RequestParam(value = "captcha")String captcha,
                               @RequestParam(value = "uuid")String uuid,
                               @RequestParam(value = "name")String name,
                               @RequestParam(value = "username")String username,
                               @RequestParam(value = "password")String password,
                               @RequestParam(value = "email",required = false)String email,
                               @RequestParam(value = "mobile",required = false)String mobile){
        if (!sysCaptchaService.validate(uuid,captcha)){
            throw new ApiException(ApiError.CODE_ERROR);
        }
        try {
            password = AESUtil.desEncrypt(password).trim();
            username = AESUtil.desEncrypt(username).trim();
        } catch (Exception e) {
            throw new ApiException("解密失败！");
        }
        String salt = RandomStringUtils.randomAlphanumeric(20);
        password = CustomUserDetailsServiceImpl.sha256(salt, password);
        SysUserEntity sysUserEntity = new SysUserEntity()
                .setName(name)
                .setUsername(username)
                .setPassword(password)
                .setSalt(salt)
                .setEmail(email)
                .setRoleId(2L)
                .setMobile(mobile);
        sysUserService.save(sysUserEntity);
        return new ResponseJSON(sysUserEntity);
    }
}
