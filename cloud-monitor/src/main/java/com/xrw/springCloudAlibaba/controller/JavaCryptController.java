package com.xrw.springCloudAlibaba.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * @program: monitor
 * @description: Md5加密, Base64的加密和Base64的解密
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2020-12-20 18:03
 **/
@RestController
@RequestMapping("/java/crypt")
public class JavaCryptController {

    /***
     * MD5加密
     * @param str 需要加密的参数
     * @return
     * @throws Exception
     */
    @RequestMapping("/encrypt_MD5")
    public String encrypt_MD5(String str) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        return new BigInteger(1,md.digest()).toString(16);
    }

    /***
     * Base64加密
     * @param str 需要加密的参数
     * @return
     * @throws Exception
     */
    @RequestMapping("/encrypt_Base64")
    public String encrypt_Base64(String str) throws Exception {
        String result = Base64.getEncoder().encodeToString(str.getBytes("UTF-8"));
        return result;
    }

    /***
     * Base64解密
     * @param str 需要解密的参数
     * @return
     * @throws Exception
     */
    @RequestMapping("/decrypt_Base64")
    public String decrypt_Base64(String str) throws Exception {
        byte[] asBytes = Base64.getDecoder().decode(str);
        String result = new String(asBytes,"UTF-8");
        return result;
    }
}
