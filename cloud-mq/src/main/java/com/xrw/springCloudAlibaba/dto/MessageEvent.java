package com.xrw.springCloudAlibaba.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @program: LightFileManagement
 * @description: 监听对象
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-02-07 17:53
 **/
@Getter
@Setter
public class MessageEvent extends ApplicationEvent {
    private static final long serialVersionUID = -4468405250074063206L;
    private DefaultMQPushConsumer consumer;
    private List<MessageExt> msgs;

    public MessageEvent(List<MessageExt> msgs, DefaultMQPushConsumer consumer) {
        super(msgs);
        this.consumer = consumer;
        this.setMsgs(msgs);
    }
}
