package com.xrw.springCloudAlibaba.config.rocketMQ;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: LightFileManagement
 * @description: 保存rocketMQ配置文件的类
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-02-07 15:07
 **/
@PropertySource("classpath:config/rocketmq.properties")
@Configuration
@ConfigurationProperties(prefix = "xrw.rocketmq")
@Setter
@Getter
@ToString
// chain = true:setter方法返回当前对象,生成的setter方法如下，方法体略 public User setId(Long id) {}
// luent的中文含义是流畅的，设置为true，则getter和setter方法的方法名都是基础属性名，且setter方法返回当前对象 public User id(Long id) {}
// @Accessors(prefix = "p")：prefix的中文含义是前缀，用于生成getter和setter方法的字段名会忽视指定前缀（遵守驼峰命名）private Long pId;去掉前缀“p”：public Long getId() {}
@Accessors(chain = true)
public class RocketMQProperties {
    private String namesrvAddr;
    private String producerGroupName;
    private String transactionProducerGroupName;
    private String consumerGroupName;
    private String producerInstanceName;
    private String consumerInstanceName;
    private String producerTranInstanceName;
    private int consumerBatchMaxSize;
    private boolean consumerBroadcasting;
    private boolean enableHistoryConsumer;
    private boolean enableOrderConsumer;
    private List<String> subscribe = new ArrayList<String>();
}
