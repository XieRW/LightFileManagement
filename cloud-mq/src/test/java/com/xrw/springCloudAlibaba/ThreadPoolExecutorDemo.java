package com.xrw.springCloudAlibaba;

import org.apache.commons.lang3.RandomUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudMqApplication.class)
public class ThreadPoolExecutorDemo {
    @Qualifier("defaultMQProducer")
    @Autowired
    private DefaultMQProducer defaultProducer;
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


    @Test
    public void test() throws ExecutionException, InterruptedException {
        System.out.println(Thread.currentThread().getName()+" Start Time:"+new Date());
        defaultProducer.getCreateTopicKey();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                WORK_QUEUE,
                THREAD_FACTORY,
                HANDLER);
        //创建100个任务，队列长度100，10个核心线程池执行任务，拒绝策略:拒绝的任务让main线程执行
//        for (int i = 0; i < 100; i++) {
//            ArrayList<Integer> list = new ArrayList<>();
//            for (int j = 0; j < 10000; j++) {
//                list.add(RandomUtils.nextInt());
//            }
//            MyRunnable runnable = new MyRunnable(list);
//            Future<?> future = executor.submit(runnable);
//            future.get();
//        }
        Integer result = 1;
        ArrayList<Integer> list = new ArrayList<>();
        FutureTask<Integer> integerFutureTask = new FutureTask<Integer>(new MyRunnable(list),result);
        Future<Integer> submit = executor.submit(integerFutureTask,result);
        Integer integer = submit.get();
        System.out.println(integer);
        //终止线程池接受新任务,已有的线程继续运行
//        executor.shutdown();
//        while (!executor.isTerminated()){}
//        System.out.println("Finished all threads!");

        System.out.println(Thread.currentThread().getName()+" End Time:"+new Date());

    }
}
