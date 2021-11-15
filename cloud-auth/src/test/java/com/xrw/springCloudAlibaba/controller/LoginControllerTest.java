package com.xrw.springCloudAlibaba.controller;

import com.xrw.springCloudAlibaba.CloudAuthApplication;
import com.xrw.springCloudAlibaba.utils.AESUtil;
import com.xrw.springCloudAlibaba.vo.ResponseJSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @program: LightFileManagement
 * @description: 测试LoginController
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-11-09 11:02
 **/
@RunWith(value = SpringRunner.class)
@SpringBootTest(classes = CloudAuthApplication.class)
public class LoginControllerTest {

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void signUp() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        String password = AESUtil.encrypt("123456").trim();
        String username = AESUtil.encrypt("test4").trim();
        map.add("captcha", "xebey");
        map.add("uuid", "2e07a816-172d-4ad2-8f17-fbfaab339377");
        map.add("name", "test4");
        map.add("username", username);
        map.add("password", password);
        map.add("email", "test1@qq.com");
        map.add("mobile", "13920002000");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<ResponseJSON> entity = restTemplate.postForEntity("http://localhost:9101/auth/permit/signUp", request, ResponseJSON.class);
        System.out.println(entity.getBody());
    }
}
