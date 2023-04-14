package com.boboo.chapter1;

import java.util.concurrent.TimeUnit;

/**
 * volatile和synchronized
 *
 * @author: boboo
 * @Date: 2023/4/11 14:17
 **/
@SuppressWarnings({"all"})
public class VolatileVisibilityDemo {
    /**
     * 比赛开始标识
     */
    public static volatile boolean ready = false;

    public static void main(String[] args) {
        // 裁判发号施令
        createReferee().start();

        // 选手就位
        createRunner("张三").start();
        createRunner("李四").start();
        createRunner("王五").start();
    }

    /**
     * 创建裁判
     */
    public static Thread createReferee() {
        return new Thread(() -> {
            System.out.println("各就各位,预备~");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception ignored) {

            }
            System.out.println("跑~砰!");
            ready = true;
        });
    }

    /**
     * 创建选手
     *
     * @param name 姓名
     */
    public static Thread createRunner(String name) {
        return new Thread(() -> {
            System.out.println(name + "已就位");
            while (!ready) {

            }
            System.out.println(name + "起跑了");
        });
    }
}
