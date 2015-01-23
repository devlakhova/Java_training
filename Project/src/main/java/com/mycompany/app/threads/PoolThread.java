package com.mycompany.app.threads;

public class PoolThread extends Thread {

    private BlockingQueue taskQueue = null;
    private boolean       isStopped = false;
    private Counter counter;

    public PoolThread(BlockingQueue queue, Counter counter){
        taskQueue = queue;
        this.counter = counter;
    }

    public void run(){
        while(!isStopped()){
            try{

                Runnable runnable = (Runnable) taskQueue.dequeue();
                runnable.run();
                counter.dec(1);

            } catch(Exception e){
                //log or otherwise report exception,
                //but keep pool thread alive.
            }
        }
    }

    public synchronized void doStop(){
        isStopped = true;
        this.interrupt(); //break pool thread out of dequeue() call.
    }

    public synchronized boolean isStopped(){
        return isStopped;
    }
}
