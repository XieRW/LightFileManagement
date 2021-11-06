package com.xrw.springCloudAlibaba.controller;

import com.xrw.springCloudAlibaba.dto.User;
import com.xrw.springCloudAlibaba.entity.SysUserEntity;
import com.xrw.springCloudAlibaba.exception.ApiError;
import com.xrw.springCloudAlibaba.exception.ApiException;
import com.xrw.springCloudAlibaba.service.SysCaptchaService;
import com.xrw.springCloudAlibaba.service.SysUserService;
import com.xrw.springCloudAlibaba.vo.ResponseJSON;
import org.apache.commons.io.IOUtils;
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
                               @RequestParam(value = "username")String username,
                               @RequestParam(value = "password")String password,
                               @RequestParam(value = "password",required = false)String email){
        if (!sysCaptchaService.validate(uuid,captcha)){
            throw new ApiException(ApiError.CODE_ERROR);
        }
        SysUserEntity sysUserEntity = new SysUserEntity().setUsername(username).setPassword(password).setEmail(email).setRoleId(2L);
        sysUserService.save(sysUserEntity);
        return new ResponseJSON(sysUserEntity);
    }
}
