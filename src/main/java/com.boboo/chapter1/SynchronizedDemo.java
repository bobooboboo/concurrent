package com.boboo.chapter1;

/**
 * synchronized字节码示例
 *
 * @author: boboo
 * @Date: 2023/4/13 8:51
 **/
public class SynchronizedDemo {
    public int num = 0;

    public final Object lock = new Object();

    /**
     * 同步方法
     */
    public synchronized void increaseBySynchronizedMethod() {
        num++;
    }

    /**
     * 同步代码块
     */
    public void increaseBySynchronizedCodeBlock() {
        synchronized (lock) {
            num++;
        }
    }
}
