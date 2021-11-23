package com.xrw.springCloudAlibaba.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @program: LightFileManagement
 * @description: 自定义线程池
 * @author: xearin 1429382875@qq.com
 * @create: 2021-11-23 14:44
 **/
@Component
public class MyThreadPoolExecutor {
    /**
     * 核心线程
     */
    private static final int CORE_POOL_SIZE = 10;
    /**
     * 最大线程
     */
    private static final int MAXIMUM_POOL_SIZE = 100;
    /**
     * 任务队列
     */
    private static final int QUEUE_CAPACITY = 100;
    /**
     * 无任务时线程存活时间
     */
    private static final Long KEEP_ALIVE_TIME = 10L;
    /**
     * 时间的单位
     */
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    /**
     * 工作队列：数组类型
     */
    private static final BlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
    /**
     * 默认的线程工厂
     */
    private static final ThreadFactory THREAD_FACTORY = Executors.defaultThreadFactory();
    /**
     * 当线程池满时，只要线程池未关闭，让调用execute方法的线程执行该任务
     */
    private static final RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

    @Bean
    public ThreadPoolExecutor MyThreadPoolExecutor1() {
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                WORK_QUEUE,
                THREAD_FACTORY,
                HANDLER);
    }
}
