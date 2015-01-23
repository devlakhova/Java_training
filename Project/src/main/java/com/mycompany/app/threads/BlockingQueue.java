package com.mycompany.app.threads;

import java.util.LinkedList;
import java.util.List;

public class BlockingQueue {

    private List queue = new LinkedList();
    private int  limit = 10;

    public BlockingQueue(int limit){
        this.limit = limit;
    }

    public int size() {
        return queue.size();
    }


    public synchronized void enqueue(Object item)
            throws InterruptedException  {
        while(this.queue.size() == this.limit) {
            wait();
        }
        if(this.queue.isEmpty()) {
            notifyAll();
        }
        this.queue.add(item);
    }


    public synchronized Object dequeue()
            throws InterruptedException{
        while(this.queue.isEmpty()){
            wait();
        }
        if(this.queue.size() == this.limit){
            notifyAll();
        }

        return this.queue.remove(0);
    }

}
