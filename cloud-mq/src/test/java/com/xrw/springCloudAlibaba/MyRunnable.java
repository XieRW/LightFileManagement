package com.xrw.springCloudAlibaba;

import com.alibaba.fastjson.JSON;
import com.xrw.springCloudAlibaba.utils.SpringContextUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class MyRunnable implements Runnable{
    private ArrayList<Integer> command;
    private DefaultMQProducer defaultProducer;
    public MyRunnable(ArrayList<Integer> command){
        this.command = command;
    }
    @Override
    public void run() {
        processCommond();
    }

    private void processCommond(){
        for (int i = 0; i < command.size(); i++) {
            String json = JSON.toJSONString(command.get(i));
            Message message = new Message("topic","white",json.getBytes());
            try {
                if (defaultProducer == null){
                    defaultProducer = SpringContextUtils.getBean("defaultMQProducer",DefaultMQProducer.class);
                }
                SendResult result = defaultProducer.send(message);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        return "MyRunnable{" +
                "command=" + command +
                '}';
    }
}
