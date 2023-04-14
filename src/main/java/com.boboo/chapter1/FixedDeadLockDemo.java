package com.boboo.chapter1;

import java.util.concurrent.TimeUnit;

/**
 *  解决死锁，通过调整锁的范围
 *
 * @author: boboo
 * @Date: 2023/4/12 17:52
 **/
@SuppressWarnings("all")
public class FixedDeadLockDemo {
    /**
     * 筷子A
     */
    private static final Object CHOPSTICKS_A = new Object();

    /**
     * 筷子B
     */
    private static final Object CHOPSTICKS_B = new Object();

    /**
     * 先拿筷子A再拿筷子B
     *
     * @param name 姓名
     */
    public static void methodA(String name) {
        synchronized (CHOPSTICKS_A) {
            System.out.println(name + ":拿到了筷子A");
            try {
                // 让出CPU执行权 但不释放锁
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception ignored) {
            }
        }

        synchronized (CHOPSTICKS_B) {
            System.out.println(name + ":拿到了筷子B");
        }
    }

    /**
     * 先拿筷子B再拿筷子A
     *
     * @param name 姓名
     */
    public static void methodB(String name) {
        synchronized (CHOPSTICKS_B) {
            System.out.println(name + ":拿到了筷子B");
            try {
                // 让出CPU执行权 但不释放锁
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception ignored) {
            }
        }

        synchronized (CHOPSTICKS_A) {
            System.out.println(name + ":拿到了筷子A");
        }
    }


    public static void main(String[] args) {
        new Thread(() -> methodA("张三")).start();
        new Thread(() -> methodB("李四")).start();
    }
}
