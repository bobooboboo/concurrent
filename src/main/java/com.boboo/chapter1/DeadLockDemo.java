package com.boboo.chapter1;

import java.util.concurrent.TimeUnit;

/**
 * 死锁示例
 *
 * @author: boboo
 * @Date: 2023/4/12 17:25
 **/
@SuppressWarnings("all")
public class DeadLockDemo {
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

            synchronized (CHOPSTICKS_B) {
                System.out.println(name + ":拿到了筷子B");
                System.out.println(name + ":凑齐一双筷子,开始吃饭");
            }
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

            synchronized (CHOPSTICKS_A) {
                System.out.println(name + ":拿到了筷子A");
                System.out.println(name + ":凑齐一双筷子,开始吃饭");
            }
        }
    }

    /**
     * <p>
     * 死锁的4个必要条件
     * <ul>
     *     <li>互斥条件：资源不能共享，只能由⼀个线程使⽤</li>
     *     <li>请求与保持条件：线程已经获得⼀些资源，但因请求其他资源发⽣阻塞，对已经获得的资源保持不释放</li>
     *     <li>不可抢占：有些资源是不可强占的，当某个线程获得这个资源后，系统不能强⾏回收，只能由线程使⽤完⾃⼰释放</li>
     *     <li>循环等待条件：多个线程形成环形链，每个都占⽤对⽅申请的下个资源</li>
     * </ul>
     * 只要发⽣死锁，上⾯的条件都成⽴；只要⼀个不满⾜，就不会发⽣死锁
     * </p>
     * <p>
     * 常⻅的解决办法：
     * <ul>
     *     <li>
     *         调整申请锁的范围
     *         @see FixedDeadLockDemo
     *     </li>
     *     <li>调整申请锁的顺序</li>
     * </ul>
     * </p>
     */
    public static void main(String[] args) {
        new Thread(() -> methodA("张三")).start();
        new Thread(() -> methodB("李四")).start();
    }
}
