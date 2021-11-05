package com.xrw.springCloudAlibaba.controller;

import com.xrw.springCloudAlibaba.service.SysCaptchaService;
import com.xrw.springCloudAlibaba.vo.ResponseJSON;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
     * 验证码
     */
    @GetMapping("/test/hello")
    public ResponseJSON hello(HttpServletResponse response, String uuid) throws IOException {
        return new ResponseJSON("hello");
    }
}
