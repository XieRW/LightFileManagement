package com.xrw.springCloudAlibaba.config;

import org.apache.coyote.http11.Http11NioProtocol;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @program: monitor
 * @description: tomcat配置文件keystorePass解密
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2020-12-20 15:36
 **/
public class MyHttp11Protocol extends Http11NioProtocol {

    @Override
    public void setKeystorePass(String certificateKeystorePassword) {
        byte[] asBytes = Base64.getDecoder().decode(certificateKeystorePassword);
        String result = null;
        try {
            result = new String(asBytes,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        super.setKeystorePass(result);
    }

}
