package com.example.fulldev.Socket;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
    public static AtomicInteger leftTicketTotal = new AtomicInteger(0);

    public static AtomicInteger selledTicketTotal = new AtomicInteger(0);

    static int num = 5000;

    public static void main(String[] args) throws InterruptedException {
        Long start = System.currentTimeMillis();
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(12, 12, 10l, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100000));

        ExecutorService executor = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(5);
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            executor.execute(() -> {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                leftTicketTotal.incrementAndGet();
                selledTicketTotal.decrementAndGet();
                semaphore.release();
                countDownLatch.countDown();

            });
        }
        countDownLatch.await();

        System.out.println("result: " + leftTicketTotal.get());
        System.out.println("end  " + "result: " + selledTicketTotal.get());
        Long end = System.currentTimeMillis();
        System.out.println("time " + (end - start));

        executor.shutdown();
    }


}
