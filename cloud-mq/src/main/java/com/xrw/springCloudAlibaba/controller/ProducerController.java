package com.xrw.springCloudAlibaba.controller;

import com.alibaba.fastjson.JSON;
import com.xrw.springCloudAlibaba.dto.User;
import com.xrw.springCloudAlibaba.exception.ApiError;
import com.xrw.springCloudAlibaba.exception.ApiException;
import com.xrw.springCloudAlibaba.vo.ResponseJSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @program: LightFileManagement
 * @description: 消息发送接口
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-02-07 18:42
 **/
@RestController
@RequestMapping("/rocket")
@Slf4j
public class ProducerController {
    @Qualifier("defaultMQProducer")
    @Autowired
    private DefaultMQProducer defaultProducer;

    @Autowired
    private TransactionMQProducer transactionProducer;

    /**
     * 发送普通消息,并发消费
     */
    @RequestMapping("/sendMessage")
    public ResponseJSON sendMsg(@RequestParam(value = "topic",required = false) String topic,
                                @RequestParam(value = "tags",required = false) String tags,
                                @RequestParam(value = "msg") String msg) {
        if ("".equals(msg)){
            throw new ApiException(ApiError.PARAMETER_NULL_ERROR);
        }
        String json = JSON.toJSONString(msg);
        Message message = new Message(Optional.ofNullable(topic).orElse("user-topic"),
                Optional.ofNullable(tags).orElse("white"),
                json.getBytes());
        try {
            SendResult result = defaultProducer.send(message);
            log.info("消息id:" + result.getMsgId() + ";" + "发送状态:" + result.getSendStatus()+ ";" +"消息体："+ msg);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ApiException(ApiError.MQ_SEND_ERROR);
        }
        return new ResponseJSON();
    }

    /**
     * 发送事务消息
     *
     * @return
     */
    @RequestMapping("/sendTransactionMess")
    public String sendTransactionMsg() {
        SendResult sendResult = null;
        try {
            // a,b,c三个值对应三个不同的状态
            String ms = "c";
            Message msg = new Message("user-topic", "white", ms.getBytes());
            // 发送事务消息
            sendResult = transactionProducer.sendMessageInTransaction(msg, (Message msg1, Object arg) -> {
                String value = "";
                if (arg instanceof String) {
                    value = (String) arg;
                }
                if (value == "") {
                    throw new RuntimeException("发送消息不能为空...");
                } else if (value == "a") {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else if (value == "b") {
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }, 4);
            System.out.println(sendResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendResult.toString();
    }

    /**
     * 支持顺序发送消息
     */
    @RequestMapping("/sendMessOrder")
    public void sendMsgOrder() {
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setId(String.valueOf(i));
            user.setUsername("jhp" + i);
            String json = JSON.toJSONString(user);
            Message msg = new Message("user-topic", "white", json.getBytes());
            try {
                defaultProducer.send(msg, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        int index = ((Integer) arg) % mqs.size();
                        return mqs.get(index);
                    }
                }, i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
