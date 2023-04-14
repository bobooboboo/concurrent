package com.boboo.chapter1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程安全操作代码示例
 *
 * @author: boboo
 * @Date: 2023/4/10 13:18
 **/
@SuppressWarnings("all")
public class SafeThreadDemo {
    /**
     * 初始值
     */
    private static final AtomicInteger ATOMIC_NUM = new AtomicInteger(0);

    /**
     * 初始值
     */
    private static int NUM = 0;

    /**
     * 锁对象
     */
    private static final Lock LOCK = new ReentrantLock();

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
     * 每次调用对ATOMIC_NUM进行increment操作
     */
    public static void increaseWithAtomic() {
        ATOMIC_NUM.incrementAndGet();
    }

    /**
     * 每次调用对NUM进行++操作
     */
    public synchronized static void increaseWithSynchronized() {
        NUM++;
    }

    /**
     * 每次调用对NUM进行++操作
     */
    public static void increaseWithLock() {
        LOCK.lock();
        try {
            NUM++;
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * 累加操作
     *
     * @param increaseStrategy 累加策略
     */
    public static void increase(IncreaseStrategy increaseStrategy) {
        switch (increaseStrategy) {
            case ATOMIC:
                increaseWithAtomic();
                break;
            case SYNCHRONIZED:
                increaseWithSynchronized();
                break;
            case LOCK:
                increaseWithLock();
                break;
        }
    }

    /**
     * 查看累计值
     *
     * @param increaseStrategy 累加策略
     * @return 累计值
     */
    public static int showNum(IncreaseStrategy increaseStrategy) {
        switch (increaseStrategy) {
            case ATOMIC:
                return ATOMIC_NUM.get();
            case SYNCHRONIZED:
            case LOCK:
                return NUM;
            default:
                return 0;
        }
    }

    /**
     * 累加策略
     */
    enum IncreaseStrategy {
        ATOMIC,
        SYNCHRONIZED,
        LOCK
    }

    public static void main(String[] args) {
        // 解决核⼼思想：把⼀个⽅法或者代码块看做⼀个整体，保证是⼀个不可分割的整体
        IncreaseStrategy increaseStrategy = IncreaseStrategy.LOCK;
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                for (int j = 0; j < LOOP_COUNT; j++) {
                    increase(increaseStrategy);
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
                System.out.println(showNum(increaseStrategy));
                break;
            }
        }
    }
}
