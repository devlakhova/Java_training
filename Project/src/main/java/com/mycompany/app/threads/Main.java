package com.mycompany.app.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Created by devlakhova on 1/20/15.
 */
public class Main implements Runnable {

    private long from;
    private long to;
    private long sum;

    public Main(long from, long to) {
        this.from = from;
        this.to = to;
    }

    public static void main(String[] args) throws InterruptedException {
        final long maxVal = 10l * 1000 * 1000 * 100;
        long interval = 1_000_000_00;
//        ExecutorService
//        Executors
//        Future
//        java.util.concurrent.Callable

        //Collections:
        //ConcurrentHashMap
//        LinkedBlockingDeque
//        LinkedBlockingQueue

//        CopyOnWriteArrayList
//        CopyOnWriteArraySet

//        Semaphore

//        AtomicInteger
//                Atomic*
//        AtomicReference

//        java.util.concurrent.locks.ReentrantLock
//        AtomicInteger

//        BlockingDeque
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        ExecutionPool ep = new ExecutionPool(Runtime.getRuntime().availableProcessors());
        List<Main> mains = new ArrayList<>();
        for (long i = 0; i < maxVal; i+= interval) {
            Main e = new Main(i, i + interval);
//            ep.submit(e);
            service.execute(e);
            mains.add(e);
        }
        service.shutdown();
        service.awaitTermination(30, TimeUnit.SECONDS);
//        ep.waitForFinish();

        long sum = 0;
        for (Main main : mains) {
            sum += main.getSum();
        }
        System.out.println(sum);
        long controlSum = 0;
        for (long i = 0; i < maxVal; i++) {
            controlSum += i;
        }
        System.out.println("Control:" + controlSum);

    }

    public long getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "Main{" +
                "from=" + from +
                ", to=" + to +
                ", sum=" + sum +
                '}';
    }

    @Override
    public void run() {
        for (long l = from; l < to; l++) {
            sum +=l;
        }
        System.out.println("Executing :" + this);
    }
}
