package com.xrw.springCloudAlibaba.service;

import com.xrw.springCloudAlibaba.dto.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: LightFileManagement
 * @description: 对消息进行消费
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-02-07 18:38
 **/
@Component
@Slf4j
public class ConsumerService {
    @EventListener(condition = "#event.msgs[0].topic=='user-topic' && #event.msgs[0].tags=='white'")
    public void rocketmqMsgListener(MessageEvent event) {
        try {
            List<MessageExt> msgs = event.getMsgs();
            for (MessageExt msg : msgs) {
                log.info("消费消息:"+new String(msg.getBody()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
