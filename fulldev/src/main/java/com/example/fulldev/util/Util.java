package com.example.fulldev.util;


import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Util extends Thread{
    private String name;

    public Util(String name) {
        this.name = name;
    }

    static {
        System.out.println("完成装载");
    }

    @Override
    public void run() {
        this.setName(this.name);
        FutureTask task=new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return "aaa";
            }
        });
        System.out.print(this.getName());
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args)throws ClassNotFoundException {
        Util util=new Util("jianlong");
        Thread thread=new Thread(util);
        thread.start();
    }
}
