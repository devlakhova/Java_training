package com.mycompany.app.threads;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devlakhova on 1/20/15.
 */
public class ExecutionPool {


    private Integer maximumPoolCapacity;
    private List<PoolThread> threads = new ArrayList<PoolThread>();
    private boolean isStopped = false;
    private Counter counter = new Counter();

    BlockingQueue taskQueue;

    public ExecutionPool(int i) {
        this.maximumPoolCapacity = i;
        taskQueue = new BlockingQueue(30);
        counter = new Counter();

        for(int k=0; k< maximumPoolCapacity; k++){
            threads.add(new PoolThread(taskQueue, counter));
        }
        for(PoolThread thread : threads){
            thread.start();
        }
    }

    public void waitForFinish() throws InterruptedException {
        while (taskQueue.size() > 0 || counter.getCount() > 0) {
            counter.waitFor(0);
//            if(taskQueue.size() == 0) {
//                for (PoolThread thread : threads) {
//                    if(thread.getState() == Thread.State.WAITING) {
//                        System.out.println("Stop thread");
//                        thread.doStop();
//                    }
//                }
//            }
        }
    }

    public void submit(Runnable e) {
        if(this.isStopped) throw
                new IllegalStateException("ThreadPool is stopped");

        try {

            counter.add(1);
            this.taskQueue.enqueue(e);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

    }


}

