package com.boboo.chapter1;

import java.util.concurrent.CountDownLatch;

/**
 * 线程不安全操作代码示例
 *
 * @author: boboo
 * @Date: 2023/4/10 13:18
 **/
@SuppressWarnings("all")
public class UnSafeThreadDemo {
    /**
     * 初始值
     */
    private static int NUM = 0;

    /**
     * 模拟线程数量
     */
    private static final int THREAD_COUNT = 10;

    /**
     * 计数器
     */
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(THREAD_COUNT);

    /**
     * 循环次数
     */
    private static final int LOOP_COUNT = 100;



    /**
     * 每次调用对NUM进行++操作
     */
    public static void increase() {
        NUM++;
    }

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                for (int j = 0; j < LOOP_COUNT; j++) {
                    increase();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //每个线程执行完成之后，调用countdownLatch
                COUNT_DOWN_LATCH.countDown();
            }).start();
        }
        while (true) {
            if (COUNT_DOWN_LATCH.getCount() == 0) {
                System.out.println(NUM);
                break;
            }
        }
    }
}
