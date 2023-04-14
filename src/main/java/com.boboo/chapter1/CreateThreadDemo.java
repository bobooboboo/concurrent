package com.boboo.chapter1;

import java.util.concurrent.*;

/**
 * 创建线程示例
 *
 * @author: boboo
 * @Date: 2023/4/10 14:58
 **/
public class CreateThreadDemo {

    /**
     * 方式一
     * 继承Thread，重写⾥⾯run⽅法，创建实例，执⾏start
     * 优点：代码编写最简单直接操作
     * 缺点：没返回值，继承⼀个类后，没法继承其他的类，拓展性差
     */
    static class MyThreadByExtendsThread extends Thread {
        @Override
        public void run() {
            System.out.println("继承Thread实现多线程");
        }
    }

    /**
     * 方式二
     * ⾃定义类实现Runnable，实现⾥⾯run⽅法，创建Thread类，使⽤Runnable接⼝的实现对象
     * 作为参数传递给Thread对象，调⽤start⽅法
     * 优点：线程类可以实现多个⼏接⼝，可以再继承⼀个类
     * 缺点：没返回值，不能直接启动，需要通过构造⼀个Thread实例传递进去启动
     */
    static class MyThreadByImplementsRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("实现Runnable实现多线程");
        }
    }

    /**
     * 方式三
     * 创建callable接⼝的实现类，并实现call⽅法，结合FutureTask类包装Callable对象，实
     * 现多线程
     * 优点：有返回值，拓展性也⾼
     * 缺点：jdk5以后才⽀持，需要重写call⽅法，结合多个类⽐如FutureTask和Thread类
     */
    static class MyThreadByImplementsCallable implements Callable<String> {
        @Override
        public String call() {
            System.out.println("实现Callable实现多线程");
            return "实现Callable实现多线程";
        }
    }

    /**
     * ⾃定义Runnable接⼝，实现run⽅法，创建线程池，调⽤执⾏⽅法并传⼊对象
     * 优点：安全⾼性能，复⽤线程
     * 缺点: jdk5后才⽀持，需要结合Runnable等进⾏使⽤
     */
    static class MyThreadByExecutorService implements Runnable {

        @Override
        public void run() {
            System.out.println("通过线程池 + Runnable实现多线程");
        }
    }

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 方式一
        MyThreadByExtendsThread myThreadByExtendsThread = new MyThreadByExtendsThread();
        myThreadByExtendsThread.start();
        // 方式二
        MyThreadByImplementsRunnable myThreadByImplementsInterface = new MyThreadByImplementsRunnable();
        // 没法直接启动
        // myThreadByImplementsInterface.run();
        new Thread(myThreadByImplementsInterface).start();
        // JDK8之后采⽤lambda表达式
        // new Thread(() -> System.out.println("实现Runnable实现多线程")).start();
        // 方式三
        FutureTask<String> futureTask = new FutureTask<>(new MyThreadByImplementsCallable());
        new Thread(futureTask).start();
        System.out.println("futureTask返回值：" + futureTask.get());
        // JDK8之后采⽤lambda表达式
        // new FutureTask<>(() -> {
        //     System.out.println("实现Callable实现多线程");
        //     return "实现Callable实现多线程";
        // });
        // 方式四
        for (int i = 0; i < 50; i++) {
            EXECUTOR_SERVICE.execute(new MyThreadByExecutorService());
        }
        EXECUTOR_SERVICE.shutdown();
    }
}
