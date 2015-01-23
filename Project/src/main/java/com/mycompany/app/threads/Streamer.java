package com.mycompany.app.threads;

import java.util.concurrent.*;

/**
 * Created by devlakhova on 1/20/15.
 */
public class Streamer{


    private int step;
    private Counter counter;

    public static void main(String[] args) throws InterruptedException {

        Streamer streamer = new Streamer();
        streamer.beginStreaming();


    }

    Streamer () {
        this.counter = new Counter();
    }

    public void beginStreaming () throws InterruptedException {
        BlockingQueue blockingQueue = new BlockingQueue(30);

        ProducerThread producer = new ProducerThread(blockingQueue);
        ConsumerThread consumer = new ConsumerThread(blockingQueue);
        counter = new Counter();
        counter.add(2);

        producer.start();
        consumer.start();
        producer.join();
        if (consumer.getState() == Thread.State.WAITING && producer.taskQueue.size() == 0) {
            consumer.interrupt();
        } else {
            consumer.join();
        }
    }

    public class ConsumerThread extends Thread {

        BlockingQueue taskQueue;

        public ConsumerThread(BlockingQueue queue) {
            this.taskQueue = queue;
        }

        @Override
        public synchronized void run() {
            while(counter.getCount() > 1 || taskQueue.size() > 0){
                try {
                    Object i = taskQueue.dequeue();
                    System.out.println(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class ProducerThread extends Thread {
        BlockingQueue taskQueue;

        public ProducerThread(BlockingQueue queue) {
            this.taskQueue = queue;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10_000_000; i++) {
                    taskQueue.enqueue(Integer.valueOf(i));
                }
                counter.dec(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
