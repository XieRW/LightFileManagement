package com.xrw.springCloudAlibaba.controller;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生成加密串的工具类接口
 *
 * @author xrw 2020-12-10
 */
@RestController
@RequestMapping("/password")
public class PasswordUtilController {

    @Autowired
    private StringEncryptor encrypt;

    @RequestMapping("/getENCPassword")
    public String getENCPassword(@RequestParam(value = "pass")String pass){
        return encrypt.encrypt(pass);
    }

    @RequestMapping("/getPassword")
    public String getPassword(@RequestParam(value = "pass")String pass){
        return encrypt.decrypt(pass);
    }
}
