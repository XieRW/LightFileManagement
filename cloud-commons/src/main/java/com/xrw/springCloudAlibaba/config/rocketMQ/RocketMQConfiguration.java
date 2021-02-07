package com.xrw.springCloudAlibaba.config.rocketMQ;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xrw.springCloudAlibaba.dto.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @program: LightFileManagement
 * @description: 加载配置文件的参数, 实例化相关的bean
 * 这个类主要加载配置文件里面参数的值,然后初始化生成producer,事务producer,consumer等实例
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-02-07 15:23
 **/
@Configuration
// 将RocketMQProperties这个属性类注入到容器中，和Component的效果一样
@EnableConfigurationProperties({RocketMQProperties.class})
@Slf4j
public class RocketMQConfiguration {

    @Autowired
    private RocketMQProperties rocketMQProperties;

    /**
     * 事件监听
     */
    @Autowired
    private ApplicationEventPublisher publisher = null;

    private static boolean isFirstSub = true;

    private static long startTime = System.currentTimeMillis();

    /**
     * 容器初始化的时候 打印参数
     */
    @PostConstruct
    public void init(){
        log.info(rocketMQProperties.getNamesrvAddr());
        log.info(rocketMQProperties.getProducerGroupName());
        log.info(String.valueOf(rocketMQProperties.getConsumerBatchMaxSize()));
        log.info(rocketMQProperties.getConsumerGroupName());
        log.info(rocketMQProperties.getConsumerInstanceName());
        log.info(rocketMQProperties.getProducerInstanceName());
        log.info(rocketMQProperties.getProducerTranInstanceName());
        log.info(rocketMQProperties.getTransactionProducerGroupName());
        log.info(String.valueOf(rocketMQProperties.isConsumerBroadcasting()));
        log.info(String.valueOf(rocketMQProperties.isEnableHistoryConsumer()));
        log.info(String.valueOf(rocketMQProperties.isEnableOrderConsumer()));
        log.info(rocketMQProperties.getSubscribe().get(0));
    }

    /**
     * @Description: 普通消息发送者实例
     * @param : 
     * @return: org.apache.rocketmq.client.producer.DefaultMQProducer
     * @Author: 谢荣旺
     * @Date: 2021/2/7
     */
    @Bean
    public DefaultMQProducer defaultMQProducer() throws MQClientException{
        DefaultMQProducer producer = new DefaultMQProducer(rocketMQProperties.getProducerGroupName());
        producer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
        producer.setInstanceName(rocketMQProperties.getProducerInstanceName());
        producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(10);
        producer.start();
        log.info("rocketmq producer server is starting....");
        return producer;
    }

    /**
     * 支持消息事务发送的实例
     * @return
     * @throws MQClientException
     */
    @Bean
    public TransactionMQProducer transactionMQProducer() throws MQClientException{
        TransactionMQProducer producer = new TransactionMQProducer(rocketMQProperties.getTransactionProducerGroupName());
        producer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
        producer.setInstanceName(rocketMQProperties.getProducerTranInstanceName());
        producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(10);
        // 事务回查最小并发数
        producer.setCheckThreadPoolMinSize(2);
        // 事务回查最大并发数
        producer.setCheckThreadPoolMaxSize(2);
        // 队列数
        producer.setCheckRequestHoldMax(2000);
        producer.start();
        log.info("rocketmq transaction producer server is starting....");
        return producer;
    }

    @Bean
    public DefaultMQPushConsumer pushConsumer() throws MQClientException{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rocketMQProperties.getConsumerGroupName());
        consumer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
        consumer.setInstanceName(rocketMQProperties.getConsumerInstanceName());

        //判断是否是广播模式
        if (rocketMQProperties.isConsumerBroadcasting()){
            consumer.setMessageModel(MessageModel.BROADCASTING);
        }
        //设置批量消费
        consumer.setConsumeMessageBatchMaxSize(rocketMQProperties
                .getConsumerBatchMaxSize() == 0 ? 1 : rocketMQProperties
                .getConsumerBatchMaxSize());

        //获取topic和tag
        List<String> subscribeList = rocketMQProperties.getSubscribe();
        for (String subscribe : subscribeList){
            consumer.subscribe(subscribe.split(":")[0],subscribe.split(":")[1]);
        }

        // 顺序消费
        if (rocketMQProperties.isEnableOrderConsumer()){
            consumer.registerMessageListener(new MessageListenerOrderly() {
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                    try {
                        context.setAutoCommit(true);
                        // 过滤消息
                        msgs = filterMessage(msgs);
                        if (msgs.size() == 0){
                            return ConsumeOrderlyStatus.SUCCESS;
                        }
                        publisher.publishEvent(new MessageEvent(msgs, consumer));
                    }catch (Exception e){
                        e.printStackTrace();
                        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });
        }
        // 并发消费
        else {
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    try {
                        // 过滤消息
                        msgs = filterMessage(msgs);
                        if (msgs.size() == 0){
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                        publisher.publishEvent(new MessageEvent(msgs, consumer));
                    }catch (Exception e){
                        e.printStackTrace();
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
        }

        // 隔五秒后再启动消费者，让spring容器初始化完成
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);

                    try {
                        consumer.start();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    log.info("rocketmq consumer server is starting....");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();

//        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
//                .setNameFormat("demo-pool-%d").build();
//        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
//                0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
//
//        singleThreadPool.execute(()-> System.out.println(Thread.currentThread().getName()));
//        singleThreadPool.shutdown();

        return consumer;
    }

    /**
     * 消息过滤器-过滤掉历史消息
     * @param msgs
     * @return
     */
    private List<MessageExt> filterMessage(List<MessageExt> msgs){
        if (isFirstSub && !rocketMQProperties.isEnableHistoryConsumer()){
            msgs = msgs.stream()
                    .filter(item -> startTime - item.getBornTimestamp() < 0)
                    .collect(Collectors.toList());
        }
        if (isFirstSub && msgs.size() > 0) {
            isFirstSub = false;
        }
        return msgs;
    }
}
